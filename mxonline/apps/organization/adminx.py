# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/10/2 12:03
""" # -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/9/16 0:19
"""
from apps.organization.models import *

import xadmin

# @xadmin.site.register(Teacher)
class TeacherAdmin(object):
    list_display = (
    'org_name','name', 'work_year', 'work_company', 'work_position', 'points', 'click_nums')
    search_fields = ('work_year', 'work_company', 'work_position')
    list_filter = ('work_year', 'work_company', 'work_position')
    model_icon = 'fa fa-user-circle-o'
    def org_name(self, obj):
        return ('%s' % obj.org.name)  # ☆☆☆☆☆

    org_name.short_description = "所在机构名称"


class CityDictAdmin(object):
    list_display = ('name', 'desc', 'add_time')
    search_fields = ('name',)
    list_filter = ('name', 'desc', 'add_time')
    # raw_id_fields = ("city",)
    model_icon = 'fa fa-list'
    # def name_city(self,obj):
    #     name = obj.name
    #     return name
    # name_city.short_description = "城市姓名"

# @xadmin.sites.register(CourseOrg)
class CourseOrgAdmin(object):
    list_display = ('name', 'desc','click_nums', 'fav_nums', 'city_name')
    search_fields = ('name', 'city')
    list_filter = ('name','add_time', 'click_nums', 'fav_nums','city__name')
    # fk_fields = ('city',)
    #  在这里如果采用了外键那么可以使用  外键__加name的格式  双下划线

    #采用这种方法就可以在 list_display 里面显示想要字段的内容
    def city_name(self,obj):
        return ("%s" %obj.city.name)
    city_name.short_description = "所在城市"


xadmin.site.register(Teacher,TeacherAdmin)
xadmin.site.register(CityDict,CityDictAdmin)
xadmin.site.register(CourseOrg,CourseOrgAdmin)