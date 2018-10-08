# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/10/2 12:02
"""
from django.contrib import admin

# Register your models here.
# -*- coding : utf-8 -*-
"""
@author : frost
@time :2018/9/16 0:20
"""
from apps.courses.models import *

import xadmin


class CourseAdmin(object):
    list_display = ('name','course_org_name','desc','detail','degree','learn_time','students','fav_nums','click_nums')
    search_fields = ('degree')
    list_filter  = ('name','degree','learn_time')
    # detail就是要显示为富文本的字段名
    style_fields = {"detail":"ueditor"}
    import_excel = True



    # 下面的这个功能存在bug 暂且找不到解决方法。放弃。。。。。
    def post(self, request,*args,**kwargs):
        #  导入逻辑
        if 'excel' in request.FILES:
            pass
        return super(CourseAdmin, self).post(request, args, kwargs)


    def course_org_name(self, obj):
        return ('%s' % obj.course_org.name)

    course_org_name.short_description = "教育机构"



class LessonAdmin(object):
    list_display = ('course','name','add_time')
    search_fields = ('course','name')
    list_filter = ('course','name','add_time')


class VideoAdmin(object):
    list_display = ('lesson', 'name', 'add_time')
    search_fields = ('lesson', 'name')
    list_filter = ('lesson', 'name', 'add_time')


class CourseResourceAdmin(object):
    list_display = ('course', 'name', 'download','add_time')
    search_fields = ('course', 'name')
    list_filter = ('course__name', 'name')


xadmin.site.register(Course,CourseAdmin)
xadmin.site.register(Lesson,LessonAdmin)
xadmin.site.register(Video,VideoAdmin)
xadmin.site.register(CourseResource,CourseResourceAdmin)


