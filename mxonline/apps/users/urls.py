# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/10/3 19:02
"""
from django.urls import path,re_path
# from apps.operation.models import UserAsk
from apps.users.views import UserCenterView,UserImageUploadView,UpdatePassView,UpdateEmailView,SendEmailCodeView
from apps.users.views import MyCoursesView,MessageView,FavHomeView,FavTeacherView,FavCourseView
app_name = 'users'

urlpatterns = [

    path('list/',UserCenterView.as_view(),name="center_information"),

    # 我的收藏这里 应该是三个页面
    path('my_fav/',FavHomeView.as_view(),name="my_fav"),
    path('my_fav/teacher',FavTeacherView.as_view(),name="fav_teacher"),
    path('my_fav/course',FavCourseView.as_view(),name="fav_course"),


    path('mine_course/',MyCoursesView.as_view(),name="mine_course"),


    path('message/',MessageView.as_view(),name="message"),




    path('image/upload/',UserImageUploadView.as_view(),name="upload_image"),

    # 重置密码
    path('update/pwd/',UpdatePassView.as_view(),name="update_pass"),

    path('send_email_code/',SendEmailCodeView.as_view(),name='send_email_code'),

    # 更新邮箱的方式url
    path('update_email/',UpdateEmailView.as_view(),name='update_email'),

]