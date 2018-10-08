"""mxonline URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path,include,re_path
from django.views.generic import TemplateView
import xadmin
from apps.users.views import LoginViews,RegisterViews,ActiveUserView,ForgetPwdView,ResetPasswordView,UserCenterView,LogoutViews
# from apps.organization.views import Org_listView
from django.views.static import serve # 处理静态文件的内部函数
from mxonline.settings import MEDIA_ROOT
    # ,STATIC_ROOT
from apps.users.views import IndexView
# from django.conf.urls import handler404, handler500

urlpatterns = [
    path('xadmin/', xadmin.site.urls),
    # path('center/', UserCenterView.as_view(), name='user_center'),
    path('', IndexView.as_view(), name="index"),
    path('login/', LoginViews.as_view(), name="login"),
    path('logout/', LogoutViews.as_view(), name="logout"),

    path('register/', RegisterViews.as_view(), name="register"),
    path('captcha/', include('captcha.urls')),
    re_path('active/(?P<active_code>.*)/', ActiveUserView.as_view(), name="active_user"),
    path('forget/', ForgetPwdView.as_view(), name="forget_pwd"),
    re_path('reset/(?P<active_code>.*)/', ResetPasswordView.as_view(), name="reset_password"),

    # 下面的页面就是重载的教育机构信息页面  部分继承的展示操作
    path('org/', include('apps.organization.urls', namespace="org")),

    # 个人中心页面的配置
    # path('center/',UserCenterView.as_view(),name='user_center'),
    # 课程详情页面
    path('course/', include('apps.courses.urls', namespace="course")),
    path('users/', include('apps.users.urls', namespace="users")),


    # 加载上传图片的问题
    re_path('media/(?P<path>.*)', serve, {"document_root": MEDIA_ROOT}),

    # 增加 访问失败的页面服务
    # re_path('static/(?P<path>.*)', serve, {"document_root": STATIC_ROOT}),

    #富文本编辑器
    path('ueditor/',include('DjangoUeditor.urls')),
]
# /media/org/2018/09/imooc_Y2Tonsq.png


handler404 = 'users.views.page_not_found'
# 全局500页面配置
handler500 = 'users.views.page_error'