from django.shortcuts import render

# Create your views here.
from django.shortcuts import render

# 判断用户是否存在  以及 登录的函数
from django.contrib.auth import authenticate,login,logout
# Create your views here.
from django.http import HttpResponse, JsonResponse,HttpResponseRedirect
from django.contrib.auth.backends import  ModelBackend
from django.db.models import Q  # 用来进行或操作
from django.views.generic.base import View
from apps.users.models import UserProfile,EmailVerifyRecord,Banner
from apps.users.forms import LoginForms,RegisterForms,ForgetForms
# 密码加密函数导入
from django.contrib.auth.hashers import make_password
# 导入邮箱邮件页面函数
from apps.utils.send_email import send_register_email
#判断用户是否登录
from apps.utils.mixin import LoginRequiredMixin
from .forms import UploadImageForms,UpdatePassForms,UserInforForms
import json
from apps.operation.models import UserCourse,UserFavorite
from apps.courses.models import Teacher,Course
from apps.organization.models import CourseOrg
from apps.operation.models import UserMessage
from pure_pagination import Paginator, EmptyPage, PageNotAnInteger

class CustomBackend(ModelBackend):
    # 继承了这个函数，后面就可以进行 邮箱 或者 姓名 的登录
    def authenticate(self, request, username=None, password=None, **kwargs):
        try:
            # get 用来防止获取两个用户  Q集合操作
            user = UserProfile.objects.get(Q(username=username)|Q(email=username))
            # 进行密码的判断
            if user.check_password(password):
                return user
        except Exception:
            return None


class ForgetPwdView(View):
    def get(self,request):
        forget_form = ForgetForms()
        # forget_form['email'] =  "邮箱"
        # 这样做就会报错 --- item assignment  不允许赋值操作
        return render(request,"forgetpwd.html",{'forget_form':forget_form})

    def post(self,request):
        forget_form = ForgetForms(request.POST)
        if forget_form.is_valid():
            # 如果是正确的信息
            email = request.POST.get('email')
            send_register_email(email, "forget")
            return render(request,'complate.html')
        else:
            # pass # 提示相应的错误信息
            # forget_err = ForgetForms()
            return render(request,'forgetpwd.html',{'forget_form':forget_form})

class ResetPasswordView(View):
    def get(self,request,active_code):
        # 在这里对激活进行确认操作 防止意外发生
        the_user = EmailVerifyRecord.objects.filter(code=active_code)
        if the_user:
            # 如果是正确的验证码 那么进行下一步  重置密码
            # pass
            for i in the_user:
                email = i.email
              # 目前感觉自己的逻辑也是可以的的    下午继续分析下老师的思路
                return render(request,"password_reset.html",{'code':active_code,'email':email})
                # return render(request, "password_reset.html", {'email':email})

        else:
            # 激活码失效
            return render(request, "active_err.html")


    # 这里肯定存在bug  逻辑思路过不去  这样应该可以了active_code就是网址后面的部分
    def post(self,request,active_code):
        count = EmailVerifyRecord.objects.filter(code=active_code)
        if count:
            for i in count :
                email = i.email
                password  = request.POST.get('password')
                password_double = request.POST.get('password2')
                if password != password_double:
                    return render(request,'password_reset.html',{'msg':'密码不一致'})
                else:
                    target_user = UserProfile.objects.get(email=email)
                    new_pass = make_password(password_double)
                    target_user.password = new_pass #更新密码
                    target_user.save()
                    #返回登录界面进行登录
                    return render(request,'login.html')
        else: # 连接码失效
            return render(request,'active_err.html')





class ActiveUserView(View):
    def get(self,request,active_code):
        all_records = EmailVerifyRecord.objects.filter(code=active_code)
        if all_records:
            for record in all_records:
                email = record.email
                user = UserProfile.objects.get(email=email)
                # 找到这个用户然后进行状态的转变
                user.is_active = True
                user.save()
        else:
            return render(request,"active_err.html")
        return render(request,"login.html")




# 注册新用户的函数
class RegisterViews(View):
    def get(self,request):
        register_form = RegisterForms()
        return render(request, "register.html",{'register_form':register_form})

    def post(self,request):
        register_form = RegisterForms(request.POST)
        if register_form.is_valid():
            # 若果用户输入的创建的信息正确 那么就可以进行入库操作
            email = request.POST.get('email', '')
            # 注册的时候 用户名重复的现象
            if UserProfile.objects.filter(username=email):
                return render(request,"register.html",{'register_form': register_form,'msg':'用户名已存在'})
            pass_word = request.POST.get("password", '')
            #在这里要进行邮箱激活验证操作
            user = UserProfile() # 实例化一个user 对象
            user.username = email
            user.is_active = False  # 这里还没有激活  所以还是0 状态
            user.email = email # 在这里 昵称 和 邮箱是一样的   原因就是使用了邮箱来创建用户
            user.password = make_password(pass_word) # 加密操作
            user.save()  # 保存进数据库
            send_register_email(email,"register")
            return render(request, "active_err.html")

            # return render(request,"激活链接")
        else:
            register_form_odd = RegisterForms()
            return render(request,"register.html",{'register_err':register_form,'register_form': register_form_odd})


# 仿照下面的登录功能 编写退出功能
class LogoutViews(View):

    def get(self,request):
        logout(request)
        from django.urls import reverse
        return HttpResponseRedirect(reverse('index'))





# 在后期的开发当中  更多的是采用 类的方法
class LoginViews(View):
    def get(self,request):
        return render(request, "login.html")
    def post(self,request):
        login_form = LoginForms(request.POST)
        if login_form.is_valid():
            user_name = request.POST.get('username', '')
            pass_word = request.POST.get("password", '')
            # print(user_name,pass_word)
            # 参数必须加上 前面的提示
            user = authenticate(username=user_name, password=pass_word)
            # ---- 跳转到上面自定义的 authenticate函数
            if user is not None:
                    # 若果用户存在的话
                if user.is_active :
                    login(request, user)
                        #成功登录的话 跳转到主页面
                    from django.urls import reverse
                    return HttpResponseRedirect(reverse('index'))
                else:
                    return render(request, 'login.html',{'msg':'账号还没有激活'})
            else:
                return render(request,'login.html',{'msg':'账号或者密码错误'})
        else:
                # 后面添加错误信息传递给前台
            return render(request, "login.html", {'login_err':login_form})

# def User_login(request):
#     if request.method == "POST":
#         # pass
#         user_name = request.POST.get('username','')
#         pass_word = request.POST.get("password",'')
#         # print(user_name,pass_word)
#         # # 参数必须加上 前面的提示
#         user = authenticate(username=user_name,password=pass_word)
#         # ---- 跳转到上面自定义的 authenticate函数
#         if user is not None:
#         #     # 若果用户存在的话
#             login(request,user)
#         #     #成功登录的话 跳转到主页面
#             return render(request,'index.html')
#         else:
#         #     # 后面添加错误信息传递给前台
#             return render(request,"login.html",{'msg':'账号或密码错误'})
#
#     #如果没有操作 一开始跳转到本页面
#     elif request.method == "GET":
#         return render(request,"login.html",{})



class UserCenterView(LoginRequiredMixin,View):

    def get(self,request):

        # 根据request 里面的id 获取当前用户信息
        return render(request,'usercenter-info.html',{

        })

    def post(self, request):
        # 应该不需要验证用户是否登录 直接修改密码

        # 后面的实例参数 是用来修改某个用户的
        information = UserInforForms(request.POST,instance=request.user)
        if information.is_valid():
            # 信息是正确的
            information.save()
            return HttpResponse('{"status":"success"}', content_type="application/json")
        else:
            return HttpResponse(json.dumps(information.errors), content_type="application/json")


# 更新用户头像
class UserImageUploadView(LoginRequiredMixin,View):

    def post(self,request):
        # 首先实例化一个对象

        # 或者在这里采用更加简便的方法 =====    modelform 结合了两者的功能，所以可以直接进行保存
        # image_form = UploadImageForms(request.POST,request.FILES,instance=request.user)
        # if image_form.is_valid():
        #     image_form.save()
        # 头像修改功能
        image_form = UploadImageForms(request.POST,request.FILES)
        if image_form.is_valid():
            image = image_form.cleaned_data['image']  # 该文件字段保存在 cleaned_data 里面
            request.user.image = image
            request.user.save()
            return HttpResponse('{"status":"success"}', content_type="application/json")
        else:
            return HttpResponse('{"status":"fail"}', content_type="application/json")


# 修改密码
class UpdatePassView(LoginRequiredMixin,View):

    def post(self, request):

        update_pass = UpdatePassForms(request.POST)
        if update_pass.is_valid():
            pass1 = request.POST.get('password1',"")
            pass2 = request.POST.get('password2',"")
            if pass1 != pass2:
                return HttpResponse('{"msg":"密码不一致"}', content_type="application/json")

            else:
                target_user = request.user
                new_pass = make_password(pass2)
                target_user.password = new_pass  # 更新密码
                target_user.save()
                # 返回登录界面进行登录
                return HttpResponse('{"status":"success"}', content_type="application/json")
        else:  # 连接码失效
            return HttpResponse(json.dumps(update_pass.errors), content_type="application/json")



# 关于邮箱的更新  这里需要用到两张表
# 1 ： 发送验证码      2： 更新表单的提交


class SendEmailCodeView(LoginRequiredMixin,View):

    def get(self,request):
        # 首先需要判断邮箱是否被注册过
        email = request.GET.get('email','')
        if UserProfile.objects.filter(email=email):
            # 如果匹配到了相同的邮箱 证明已经被用过  不可以再次注册
            return HttpResponse('{"email":"邮箱已经被占用"}', content_type="application/json")
        else:
            # 没有匹配到 可以发送验证码
            send_register_email(email,send_type='update_email')
            return HttpResponse('{"status":"success"}', content_type="application/json")


# 修改邮箱
class UpdateEmailView(LoginRequiredMixin,View):

    # 现在的处理方法就不一样了 应该是post 接受
    def post(self,request):
        # first 确认该链接是否有效
        email = request.POST.get('email','')
        code = request.POST.get('code','')

        exist_record = EmailVerifyRecord.objects.filter(email=email,code=code,send_type='update_email')
        if exist_record:
            #如果真的存在该链接记录 那么就可以执行入库保存工作
            the_user = request.user
            the_user.email = email
            the_user.save()
            return HttpResponse('{"status":"success"}', content_type="application/json")
        else:
            # 没有相关的信息  链接无效
            return HttpResponse('{"email":"验证码错误"}', content_type="application/json")


# 我的收藏三个栏目的设置
class FavHomeView(LoginRequiredMixin,View):

    def get(self,request):
        # 首先需要把收藏的机构导入
        org_list = list()
        fav_objects = UserFavorite.objects.filter(user=request.user,fav_type=2)
        for ob in fav_objects :
            the_id = ob.fav_id
            org = CourseOrg.objects.get(id = the_id)
            org_list.append(org)

        return render(request,'usercenter-fav-org.html',{'org_list':org_list})


class FavTeacherView(LoginRequiredMixin,View):

    def get(self,request):
        # 返回用户收藏的所有教师信息
        teacher_list = list()
        fav_objects = UserFavorite.objects.filter(user= request.user,fav_type=3)
        for ob in fav_objects:
            the_id = ob.fav_id
            teacher = Teacher.objects.get(id = the_id)
            teacher_list.append(teacher)

        return render(request,'usercenter-fav-teacher.html',{"teacher_list":teacher_list})


class FavCourseView(LoginRequiredMixin,View):

    def get(self,request):
        # 返回用户收藏的所有课程
        course_list = list()
        # fav_objects = UserFavorite.objects.filter(user=request.user, fav_type=1)
        # for ob in fav_objects:
        #     the_id = ob.fav_id
        #     course = Course.objects.get(id=the_id)
        #     course_list.append(course)

        return render(request,'usercenter-fav-course.html',{"course_list":course_list})


# 我的课程
class MyCoursesView(LoginRequiredMixin,View):

    def get(self, request):
        # 根据用户id 获取所有的课程信息
        courses = UserCourse.objects.filter(user=request.user)
        return render(request, 'usercenter-mycourse.html', {'courses':courses})


#用户消息
class MessageView(LoginRequiredMixin,View):

    def get(self, request):
        # 返回所有的消息
        message = UserMessage.objects.filter(user = request.user.id)

        # 点击了消息按钮 那么所有的消息都变成 已读
        all_false_messages = UserMessage.objects.filter(has_read=False)
        for mess in all_false_messages:
            mess.has_read = True
            mess.save()

        try:
            page = request.GET.get('page', 1)
        except PageNotAnInteger:
            page = 1
        objects = message  # 所有机构的信息 传递给 objects
        p = Paginator(objects, 3, request=request)  # 注意这里文档给的是错误的信息，需要加上每页展示的机构数目
        messages = p.page(page)

        return render(request, 'usercenter-message.html', {"messages":messages})


class IndexView(View):

    def get(self,request):
        title_banners =  Banner.objects.all().order_by('-index') # 所有的轮播图片素材

        course_banners = Course.objects.all()
        courses = Course.objects.all()[:6]

        orgs = CourseOrg.objects.all()[:15]
        return render(request,'index.html',{
            'title_banners':title_banners,
            'course_banners':course_banners,
            'courses':courses,
            'orgs':orgs,
        })


#页面访问失败
def page_not_found(request):
    from django.shortcuts import render_to_response
    response = render_to_response('404.html')
    response.status_code = 404
    return response


def page_error(request):
    from django.shortcuts import render_to_response
    response = render_to_response('500.html')
    response.status_code = 500
    return response

