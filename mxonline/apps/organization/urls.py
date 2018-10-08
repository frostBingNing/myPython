# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/9/20 21:21
"""

from django.urls import path,re_path
# from apps.operation.models import UserAsk
from .views import Org_listView,UserAskView,OrgHomeView,OrgDescView,OrgTeachView,OrgCourView
from .views import AddFavView,TeacherListView,TeacherDetailView
app_name = 'org'
# 如果前面采用了 namespace 那么这里需要添加进去相应的信息

# 'set' object is not reversible 如果下面利用 {} 就会报错
urlpatterns = [
    path('list/',Org_listView.as_view(),name="org_list"),
    path('add_ask/',UserAskView.as_view(),name="add_ask"),
    # 机构首页的显示页面
    re_path('org_home/(?P<org_id>\d+)',OrgHomeView.as_view(),name="org_home"),
    re_path('desc/(?P<org_id>\d+)',OrgDescView.as_view(),name="org_desc"),
    re_path('org_teacher/(?P<org_id>\d+)',OrgTeachView.as_view(),name="org_teach"),
    re_path('org_course/(?P<org_id>\d+)',OrgCourView.as_view(),name='org_cour'),

    # 用户收藏功能的设置
    path('add_fav/', AddFavView.as_view(), name="add_fav"),

    # 教师信息列表页面
    path('teacher/list/', TeacherListView.as_view(), name="teacher_list"),
    re_path('teacher/(?P<teacher_id>\d+)', TeacherDetailView.as_view(), name="teacher_detail"),

]