# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/9/28 16:45
"""

from django.urls import path,re_path
from .views import CourseView,CourseDetailView,CourseLearnView,CourseCommentView,AddCommentView,VideoPlayView
# from apps.operation.models import UserAsk

app_name = 'course'
# 如果前面采用了 namespace 那么这里需要添加进去相应的信息

# 'set' object is not reversible 如果下面利用 {} 就会报错
urlpatterns = [
    path('list/',CourseView.as_view(),name="course_list"),
    re_path('detail/(?P<course_id>\d+)', CourseDetailView.as_view(), name='course_detail'),
    # path('add_ask/',UserAskView.as_view(),name="add_ask"),
    # 下面的链接是开始学习页面的配置
    re_path('learn/(?P<course_id>\d+)', CourseLearnView.as_view(), name='course_learn'),
    # 课程评论页面
    re_path('comment/(?P<course_id>\d+)', CourseCommentView.as_view(), name='course_comment'),

    # 视频播放页面
    re_path('video/(?P<video_id>\d+)', VideoPlayView.as_view(), name='video_play'),

    # 添加用户评论
    path('add_comment/', AddCommentView.as_view(), name='add_comment'),

]