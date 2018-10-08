from django.shortcuts import render

# Create your views here.
from django.shortcuts import render
from django.views.generic import View
from pure_pagination import Paginator, EmptyPage, PageNotAnInteger
from django.http import HttpResponse, JsonResponse
from django.db.models import Q

from apps.operation.models import UserFavorite, CourseComments, UserCourse
from .models import Course, Video

from apps.utils.mixin import LoginRequiredMixin


class CourseView(View):
    def get(self, request):

        # 接下来就是处理数据了
        all_courses = Course.objects.all()  # 返回所有的课程信息
        all_courses = all_courses.order_by('-add_time')
        # 热门课程推荐
        hot = Course.objects.all()
        hot_courses = hot.order_by('-click_nums')[:3]

        # 在排序之前进行一次筛选

        search_keywords = request.GET.get('keywords', '')
        if search_keywords:
            # 如果存在相关关键词的话
            # name  desc detail 三者其一满足 就可以进行下一步
            all_courses = all_courses.filter(
                Q(name__icontains=search_keywords) | Q(desc__icontains=search_keywords) | Q(
                    detail__icontains=search_keywords))

        # 接下来就是排序的部分 然后可以进行一个总结
        # 按照热门排序
        sort = request.GET.get('sort', '')
        if sort:
            if sort == 'hot':
                all_courses = all_courses.order_by('-click_nums')
            # 按照参与人数（学习人数）进行相应的排序
            if sort == 'students':
                all_courses = all_courses.order_by('-students')

        # 分页功能的实现
        try:
            page = request.GET.get('page', 1)
        except PageNotAnInteger:
            page = 1
        objects = all_courses  # 所有机构的信息 传递给 objects
        p = Paginator(objects, 6, request=request)  # 注意这里文档给的是错误的信息，需要加上每页展示的机构数目
        whole_courses = p.page(page)

        return render(request, "course_list.html", {"all_courses": all_courses, "whole_courses": whole_courses,
                                                    "sort": sort, "hot_courses": hot_courses})

    def post(self, request):
        return render(request, 'course_list.html', {})


class CourseDetailView(View):
    def get(self, request, course_id):

        # 现在是带着course_id 进来的 所以可以按照这个字段提取数据
        global relate_course  # 定义成全局变量

        course_detail = Course.objects.get(id=int(course_id))
        course_detail.click_nums += 1
        course_detail.save()

        # 相关课程推荐
        # relate_course = None
        tag = course_detail.tag
        if tag == "" or tag:
            # 存在标签的话
            relate_course = Course.objects.filter(tag=tag)[:1]

        # 下面如果不采用 判断用户是否登录  会报错
        # Value: 'AnonymousUser'
        # if UserFavorite.objects.filter(user=request.user, fav_type=2, av_id=teacher.org.id): 1
        # 错误分析，此功能必须经过验证登录的用户才能使用，没登录的用户使用了此功能导致报错，解决办法，在报错的代码前面加上如下代码：
        #
        # if request.user.is_authenticated():
        #     if UserFavorite.objects.filter(user=request.user, fav_type=3, fav_id=teacher.id):

        # 收藏功能的优化
        had_fav_course = False
        had_fav_org = False
        if request.user.is_authenticated:
            exist_record = UserFavorite.objects.filter(user=request.user, fav_id=course_detail.id, fav_type=1)
            if exist_record:
                had_fav_course = True
            exist_org = UserFavorite.objects.filter(user=request.user, fav_id=course_detail.course_org.id, fav_type=2)
            if exist_org:
                had_fav_org = True

        return render(request, 'course-detail.html', {"course_detail": course_detail,
                                                      "had_fav_org": had_fav_org, "had_fav_course": had_fav_course,
                                                      "relate_course": relate_course})


class CourseLearnView(LoginRequiredMixin, View):
    def get(self, request, course_id):
        # 课程信息的返回
        course_information = Course.objects.get(id=int(course_id))
        course_information.students += 1
        course_information.save()

        # 判断该课程是否关联   学习课程表是否添加数据
        had_learned = UserCourse.objects.filter(user=request.user, course=course_information.id)
        if not had_learned:
            # 没有进行关联
            temp = UserCourse(user=request.user, course=course_information)
            temp.save()

        # the_count = course_information.get_part_num()
        learn_objects = UserCourse.objects.filter(course=course_information.id)
        # 获取相关学生的id
        user_ids = [learn.user.id for learn in learn_objects]
        # 紧接着利用学生id  在学习课程 表单里面获取 课程id
        #  这里采用新的方法 -----  若果使用了外键 外键_id__(双下滑线)in  可以迭代后面的内容寻找目标
        others = UserCourse.objects.filter(user_id__in=user_ids)
        # 获取相应课程的id
        others_course_ids = [one.course.id for one in others]
        # 根据最终的课程id 获取信息
        other_courses = Course.objects.filter(id__in=others_course_ids).order_by('-click_nums')[:3]

        return render(request, 'course-video.html', {"course_information": course_information,
                                                     "relate_courses": other_courses
                                                     })


class CourseCommentView(LoginRequiredMixin, View):
    def get(self, request, course_id):
        course_information = Course.objects.get(id=int(course_id))

        # 返回相关课程的推荐
        # 首先找到跟这门课程相关的对象
        learn_objects = UserCourse.objects.filter(course=course_information.id)
        # 获取相关学生的id
        user_ids = [learn.user.id for learn in learn_objects]
        # 紧接着利用学生id  在学习课程 表单里面获取 课程id
        #  这里采用新的方法 -----  若果使用了外键 外键_id__(双下滑线)in  可以迭代后面的内容寻找目标
        others = UserCourse.objects.filter(user_id__in=user_ids)
        # 获取相应课程的id
        others_course_ids = [one.course.id for one in others]
        # 根据最终的课程id 获取信息
        other_courses = Course.objects.filter(id__in=others_course_ids).order_by('-click_nums')[:3]

        # reload 当前页面的时候  需要把所有的评论获取
        all_comments = CourseComments.objects.filter(user=request.user, course=course_id)
        order_commnets = all_comments.order_by('-add_time')
        return render(request, 'course-comment.html', {"course_information": course_information,
                                                       "all_comments": order_commnets,
                                                       "relate_courses": other_courses})


class AddCommentView(View):
    def post(self, request):
        # 需要判断用户是否登录 不可以匿名提交
        if not request.user.is_authenticated:
            # 未登录时返回json提示未登录，跳转到登录页面是在ajax中做的
            return HttpResponse('{"status":"fail", "msg":"用户未登录"}', content_type='application/json')
            # else:
            #     return HttpResponse('{"status":"fail", "msg":"用户未登录"}', content_type='application/json')

            #  添加数据
        course_id = request.POST.get('course_id', 0)
        comment = request.POST.get('comments', "")
        if int(course_id) > 0 and comment != "":
            # 这里等价于信息是正确的访问源
            comment_infor = CourseComments()
            user = request.user
            course = Course.objects.get(id=int(course_id))
            comment_infor.course = course
            comment_infor.comments = comment
            comment_infor.user = user
            comment_infor.save()
            return HttpResponse('{"status":"success","msg":"添加成功"}', content_type="application/json")
        else:
            # 信息是错误的访问源
            return HttpResponse('{"status":"fail","msg":"添加失败"}', content_type="application/json")


class VideoPlayView(LoginRequiredMixin, View):

    def get(self, request, video_id):
        # 获取视频对象

        # 如果是下面的这种错误 ---   videos = Video.objects.filter(id = int(video.id)) 'QuerySet' object has no attribute 'lesson'
        videos = Video.objects.get(id=int(video_id))

        # 现在观看一个视频 --- 学习人数就增加一个
        course = videos.lesson.course
        course.students += 1
        course.save()

        # 应该是多余的一步
        had_learned = UserCourse.objects.filter(user=request.user, course=course)
        if not had_learned:
            # 没有进行关联
            temp = UserCourse(user=request.user, course=course)
            temp.save()

        # 下面获得相关的其他信息
        learn_objects = UserCourse.objects.filter(course=course)
        # 获取相关学生的id
        user_ids = [learn.user.id for learn in learn_objects]
        # 紧接着利用学生id  在学习课程 表单里面获取 课程id
        #  这里采用新的方法 -----  若果使用了外键 外键_id__(双下滑线)in  可以迭代后面的内容寻找目标
        others = UserCourse.objects.filter(user_id__in=user_ids)
        # 获取相应课程的id
        others_course_ids = [one.course.id for one in others]
        # 根据最终的课程id 获取信息
        other_courses = Course.objects.filter(id__in=others_course_ids).order_by('-click_nums')[:3]

        return render(request, 'course-play.html', {
            "relate_courses": other_courses,
            'course_information': course,
            'videos': videos,
        })
