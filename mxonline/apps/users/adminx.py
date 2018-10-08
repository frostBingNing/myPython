# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/10/2 11:55
"""
# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/9/15 23:56
"""

from apps.users.models import *
from xadmin import views
import xadmin


class BaseSetting(object):
    enable_themes = True
    use_bootswatch = True  # 阔以选择的主题 默认的是本地的，速度比较快


class GlobalSetting(object):
    site_title = "霜城后台管理系统"
    site_footer = "慕学在线"
    menu_style = "accordion"  #  折叠 就是阔以隐藏

class EmailVerifyRecordAdmin(object):
    list_display = ('code','email','send_type','send_time')
    search_fields = ('send_type','code','email')
    list_filter =  ('code','email','send_type','send_time')

class BannerAdmin(object):
    list_display = ('title', 'url', 'image', 'index','add_time')
    search_fields = ('title', 'index', 'image')
    list_filter = ('title', 'url', 'image', 'index','add_time')
    model_icon = 'fa fa-envelope-o'

# 将这些表单注册进admin里面

xadmin.site.register(EmailVerifyRecord,EmailVerifyRecordAdmin)
xadmin.site.register(Banner,BannerAdmin)
xadmin.site.register(views.BaseAdminView,BaseSetting) # 主题设置
xadmin.site.register(views.CommAdminView,GlobalSetting) # 页头 页尾的设置
