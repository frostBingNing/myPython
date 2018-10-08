from django.db import models

# Create your models here.
from django.db import models

# Create your models here.

from datetime import datetime

# from apps.users.models import UserProfile
from apps.users.models import UserProfile
from django.db import models
# from apps.courses.models import Course
from apps.courses.models import Course

#  借着这次机会 正好整理完前面所有的资料！！！！      加油 --- 以后记得备份哦
class UserAsk(models.Model):
    name = models.CharField(max_length=20,verbose_name="姓名")
    mobile = models.CharField(max_length=11,verbose_name="手机")
    course_name = models.CharField(max_length=50,verbose_name="课程名称")
    add_time = models.DateTimeField(default=datetime.now,verbose_name="添加时间")

    # 定义在后台的显示方式
    class Meta:
        verbose_name = "用户咨询"  # 单数名称
        verbose_name_plural = verbose_name  #  负数名称  确保和前面的verbose_name  一致


class UserMessage(models.Model):
    #用户消息 还有 系统消息
    #直接保存用户id  因为系统直接发送全部的 而定向的是特定的
    user = models.IntegerField(default = 0 ,verbose_name="接受用户")
    message = models.CharField(max_length= 500,verbose_name="消息")
    has_read = models.BooleanField(default=False,verbose_name="是否阅读")
    add_time = models.DateTimeField(default =datetime.now,verbose_name="添加时间")

    class Meta:
        verbose_name = "用户消息"
        verbose_name_plural  = verbose_name



class CourseComments(models.Model):
    # 课程评论
    user = models.ForeignKey(UserProfile,on_delete=models.CASCADE,verbose_name="用户")
    course = models.ForeignKey(Course,on_delete=models.CASCADE,verbose_name="课程")
    comments = models.CharField(max_length=250,verbose_name="评论")
    add_time = models.DateTimeField(default = datetime.now,verbose_name="添加时间")

    class Meta:
        verbose_name = "课程评论"
        verbose_name_plural = verbose_name


class UserCourse(models.Model):
    # 用户学习的课程
    user = models.ForeignKey(UserProfile,on_delete=models.CASCADE,verbose_name="用户")
    course = models.ForeignKey(Course,on_delete=models.CASCADE,verbose_name="课程")
    add_time = models.DateTimeField(default = datetime.now,verbose_name="添加时间")


    class Meta:
        verbose_name = "学习课程"
        verbose_name_plural = verbose_name

class UserFavorite(models.Model):
    # 用户收藏栏目
    user = models.ForeignKey(UserProfile,on_delete=models.CASCADE,verbose_name="用户")
    fav_id = models.IntegerField(default = 0 ,verbose_name="数据id" )
    fav_type = models.IntegerField(choices=((1,"课程"),(2,"课程机构"),(3,"老师")),default=1,verbose_name="收藏种类")
    add_time = models.DateTimeField(default = datetime.now,verbose_name="添加时间")

    class Meta:
        verbose_name = "用户收藏"
        verbose_name_plural = verbose_name
