from django.shortcuts import render

# Create your views here.
from django.shortcuts import render
from django.views.generic import View
# Create your views here.
from apps.organization.models import CourseOrg,CityDict,Teacher
# from mxonline.settings import   MEDIA_URL
# from django.shortcuts import render_to_response
from pure_pagination import Paginator, EmptyPage, PageNotAnInteger
from apps.organization.forms import UserAskForm
from django.http import HttpResponse, JsonResponse
from apps.operation.models import UserFavorite
from django.db.models import Q
from apps.courses.models import Course

class Org_listView(View):
    def get(self,request):
        citys = CityDict.objects.all()  # 所有的城市
        # count = citys.count()           # 统计所有机构的数目
        orgs = CourseOrg.objects.all()  # 所有机构的信息

        search_keywords = request.GET.get('keywords', '')
        if search_keywords:
            # 如果存在相关关键词的话
            # name  desc detail 三者其一满足 就可以进行下一步
            orgs = orgs.filter(
                Q(name__icontains=search_keywords) | Q(desc__icontains=search_keywords))

        org_order = orgs.order_by('-click_nums')[:3]  # 取出来前三名
        # 如果这里 没有加上前面的 -  那么就不会降序排列
        #  对用户所选的栏目进行筛选

        # 筛选城市地址
        city_id = request.GET.get('city',"")
        if city_id:
            orgs = orgs.filter(city_id=int(city_id))

        # 筛选机构类别
        company_type = request.GET.get('ct',"")  # 把网址里面的字段提取出来
        if company_type:
            orgs = orgs.filter(company_type=company_type)

        # 可能还有进一步的筛选  逻辑代码写在下面
        sort = request.GET.get('sort',"")
        if sort == "students":
            orgs = orgs.order_by('-students')
        elif sort == "courses":
            orgs = orgs.order_by('-courses')

        count = orgs.count()
        try:
            page = request.GET.get('page', 1)
        except PageNotAnInteger:
            page = 1
        objects = orgs  # 所有机构的信息 传递给 objects
        p = Paginator(objects, 3 ,request=request)  # 注意这里文档给的是错误的信息，需要加上每页展示的机构数目
        whole_orgs = p.page(page)

        return render(request, 'org-list.html',
                      {'all_city': citys,
                       # 'all_org': orgs,
                       'count':count,
                       'whole_orgs':whole_orgs,
                       'order_org':org_order,
                       'city_id':city_id,  # 当前页面返回的城市id
                       'company_type':company_type,  # 返回当期页面的机构类别id
                       'sort':sort,  # 进一步筛选的条件
                       }
                      )
        # try:
        #     page = request.GET.get('page', 1)
        # except PageNotAnInteger:
        #     page = 1
        #
        # objects = ['john', 'edward', 'josh', 'frank']
        #
        # # Provide Paginator with the request object for complete querystring generation
        #
        # p = Paginator(objects, request=request)
        #
        # people = p.page(page)
        #
        # return render_to_response('index.html', {
        #     'people': people,


class UserAskView(View):
    # def get(self,request):
    #     pass

    # 采用异步的方式
    def post(self,request):
        user_ask_form = UserAskForm(request.POST)
        if user_ask_form.is_valid():
            user_ask_form.save(commit=True)   # 如果数据合法 那么进行入库操作
            # 如果添加信息成功 返回json字符串  后面content_type 指定了返回的格式
            return HttpResponse('{"status":"success"}',content_type="application/json")
        else:
            # 如果数据不合法 那么进行数据格式的提醒
            # return HttpResponse("{'status':'fail'},{'msg':{0}}".format(user_ask_form.errors))
            return HttpResponse('{"status":"fail","msg":"添加信息出错"}', content_type="application/json")
            # return JsonResponse({"status":"fail","msg":"添加信息出错"})

class OrgHomeView(View):
    def get(self,request,org_id):
        course_org =  CourseOrg.objects.get(id=int(org_id))  # 获取机构id对象
        course_org.click_nums += 1
        course_org.save()

        all_courses = course_org.course_set.all()[:3]           # 相应的课程信息
        all_teachers = course_org.teacher_set.all()[:1]          # 教师信息
        select = "org_home"
        # 判断当前用户是否登录，如果登录那么继续判断是否收藏
        had_fav = False
        if request.user.is_authenticated:
            exist_records = UserFavorite.objects.filter(user=request.user, fav_id=int(course_org.id), fav_type=2)
            if exist_records:
                # 如果是真值 那么表示真的存在相应的数据
                had_fav = True
        return render(request,'org-detail-homepage.html',{'all_courses':all_courses,'all_teachers':all_teachers,
                                'course_org':course_org,"select":select,"had_fav":had_fav})

# 机构介绍页面的返回信息

class OrgDescView(View):
    def get(self,request,org_id):
        course_org =  CourseOrg.objects.get(id=int(org_id))  # 获取机构id对象
        # all_courses = course_org.course_set.all()[:3]           # 相应的课程信息
        # all_teachers = course_org.teacher_set.all()[:1]          # 教师信息
        select = "org_dec"
        had_fav = False
        if request.user.is_authenticated:
            exist_records = UserFavorite.objects.filter(user=request.user, fav_id=int(course_org.id), fav_type=2)
            if exist_records:
                # 如果是真值 那么表示真的存在相应的数据
                had_fav = True
        return render(request,'org-detail-desc.html',{'course_org':course_org,"select":select,"had_fav":had_fav})


# 教育机构教师信息合集
class OrgTeachView(View):
    def get(self, request, org_id):
        course_org = CourseOrg.objects.get(id=int(org_id))  # 获取机构id对象
        # all_courses = course_org.course_set.all()[:3]           # 相应的课程信息
        all_teachers = course_org.teacher_set.all()        # 教师信息
        select = "org_tea"

        had_fav = False
        if request.user.is_authenticated:
            exist_records = UserFavorite.objects.filter(user=request.user, fav_id=int(course_org.id), fav_type=2)
            if exist_records:
                # 如果是真值 那么表示真的存在相应的数据
                had_fav = True
        return render(request, 'org-detail-teachers.html', {'course_org': course_org,'all_teachers':all_teachers,"select":select,"had_fav":had_fav})

# 教育机构课程信息
class OrgCourView(View):
    def get(self, request, org_id):
        course_org = CourseOrg.objects.get(id=int(org_id))  # 获取机构id对象
        all_courses = course_org.course_set.all()           # 相应的课程信息
        # all_teachers = course_org.teacher_set.all()        # 教师信息
        select = "org_course"
        had_fav = False
        if request.user.is_authenticated:
            exist_records = UserFavorite.objects.filter(user=request.user, fav_id=int(course_org.id), fav_type=2)
            if exist_records:
                # 如果是真值 那么表示真的存在相应的数据
                had_fav = True
        return render(request, 'org-detail-course.html', {'course_org': course_org,'all_courses':all_courses,"select":select,"had_fav":had_fav})


class AddFavView(View):
    # 这里的用户id 为什么可以自动存入  是因为调用的外键吧
    def post(self,request):

        fav_id = request.POST.get('fav_id', 0)
        fav_type = request.POST.get('fav_type', 0)

        if not request.user.is_authenticated:
            # 未登录时返回json提示未登录，跳转到登录页面是在ajax中做的
            return HttpResponse('{"status":"fail", "msg":"用户未登录"}', content_type='application/json')
        # else:
        #     return HttpResponse('{"status":"fail", "msg":"用户未登录"}', content_type='application/json')

        #

        exist_records  = UserFavorite.objects.filter(user=request.user,fav_id=int(fav_id),fav_type=int(fav_type))
        if exist_records :
            # 存在的情况下  重复点击==取消收藏

            exist_records.delete()
            # 取消收藏之前 先把收藏数目减一
            # 1-3  分别是 课程 机构 老师
            if int(fav_type) == 1:
                course = Course.objects.get(id = int(fav_id))
                course.fav_nums -= 1
                if course.fav_nums <= 0:
                    course.fav_nums = 0
                course.save()
            elif int(fav_type) == 2:
                org = CourseOrg.objects.get(id = int(fav_id))
                org.fav_nums -= 1
                if org.fav_nums <= 0:
                    org.fav_nums = 0
                org.save()
            elif int(fav_type)== 3:
                tea = Teacher.objects.get(id = int(fav_id))
                tea.fav_nums -= 1
                if tea.fav_nums <= 0:
                    tea.fav_nums = 0
                tea.save()
            return HttpResponse('{"status":"success","msg":"收藏"}',content_type="application/json")
        else:
            user_fav = UserFavorite()
            if int(fav_id)>0 and int(fav_type)>0 :

                if int(fav_type) == 1:
                    target = Course.objects.get(id = int(fav_id))
                    target.fav_nums += 1
                    target.save()
                elif int(fav_type) == 2:
                    target = CourseOrg.objects.get(id = int(fav_id))
                    target.fav_nums += 1
                    target.save()
                else:
                    target = Teacher.objects.get(id = int(fav_id))
                    target.fav_nums += 1
                    target.save()
                user_fav.user = request.user
                user_fav.fav_id = fav_id
                user_fav.fav_type = fav_type
                user_fav.save()
                return HttpResponse('{"status":"success","msg":"已收藏"}',content_type="application/json")
            else:
                return HttpResponse('{"status":"fail","msg":"收藏失败"}',content_type="application/json")



    # UserFavorite


class TeacherListView(View):

    def get(self,request):

        # 获得所有教师的信息
        all_teachers = Teacher.objects.all()

        search_keywords = request.GET.get('keywords', '')
        if search_keywords:
            # 如果存在相关关键词的话
            # name  desc detail 三者其一满足 就可以进行下一步
            all_teachers = all_teachers.filter(Q(name__icontains=search_keywords) | Q(work_company__icontains=search_keywords)|Q(work_position__icontains=search_keywords))

        # 教师信息的总数
        # the_count = Teacher.objects.all().count()
        # 教师信息分页

        # 增加排序功能
        sort = request.GET.get('sort','')
        if sort:
            if sort == 'hot':
                all_teachers = all_teachers.order_by('-click_nums')

        #  下面是没有排序的例子
        # 分页功能的实现
        try:
            page = request.GET.get('page', 1)
        except PageNotAnInteger:
            page = 1
        objects = all_teachers  # 所有机构的信息 传递给 objects
        p = Paginator(objects, 3, request=request)  # 注意这里文档给的是错误的信息，需要加上每页展示的机构数目
        whole_courses = p.page(page)

        # 右边栏目还有一个教师排行榜  -- 按照学生数目排序？！
        rank_teachers = all_teachers.order_by('-click_nums')[:3]

        return render(request,'teachers-list.html',{
            'all_teachers':all_teachers,
            'whole_teachers':whole_courses,
            'sort':sort,
            'rank_teachers':rank_teachers
        })


# 教师信息详情页面

class TeacherDetailView(View):
    def get(self,request,teacher_id):
        # 暂且返回为空
        # 首先是根据id 返回当前老师的信息

        the_teacher = Teacher.objects.get(id=teacher_id)
        the_teacher.click_nums += 1
        the_teacher.save()
        # 接着把教师排行榜返回
        all_teachers = Teacher.objects.all()
        rank_teachers = all_teachers.order_by('-click_nums')[:3]

        # 老师所属机构  ps  因为是教师表 引用了 机构 所以可以这样获得机构对象
        the_org = the_teacher.org

        # 老师的所有课程
        # 直接调用了相应的函数

        # 保持是否收藏的状态
        has_fav_teacher = False
        has_fav_org = False
        if request.user.is_authenticated:
            exist_records = UserFavorite.objects.filter(user=request.user, fav_id=int(the_org.id), fav_type=2)
            if exist_records:
                # 如果是真值 那么表示真的存在相应的数据
                has_fav_org = True

            information_of_teacher = UserFavorite.objects.filter(user=request.user,fav_id=int(the_teacher.id),fav_type=3)
            if information_of_teacher:
                has_fav_teacher = True

        return render(request,'teacher-detail.html',{
            'the_teacher' : the_teacher,
            'rank_teachers':rank_teachers,
            'the_org': the_org,
            'has_fav_teacher':has_fav_teacher,
            'has_fav_org':has_fav_org,
        })