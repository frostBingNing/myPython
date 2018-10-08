from datetime import datetime

from django.db import models

from apps.organization.models import  CourseOrg,Teacher
from DjangoUeditor.models import UEditorWidget,UEditorField

# Create your models here.
class Course(models.Model):
    course_org = models.ForeignKey(CourseOrg,verbose_name="所属机构",null=True,on_delete=models.CASCADE)
    name = models.CharField(max_length=50,verbose_name="课程")
    tag = models.CharField(max_length=10, verbose_name="标签",default="")
    course_type = models.CharField(max_length=20,default="后端开发",verbose_name="课程类别")
    desc = models.CharField(max_length=200,verbose_name="课程描述")

    # 下面的detail 字段进行文本编辑设置
    detail = UEditorField(verbose_name='课程详情', width=600, height=300, imagePath="courses/ueditor/",
                          filePath="courses/ueditor/", default='')
    # detail = models.TextField(verbose_name="课程详情")
    degree = models.CharField(choices=(('cj','初级'),('zj','中级'),('gj','高级')),max_length=2)
    learn_time = models.IntegerField(default=0,verbose_name="学习时长")
    students = models.IntegerField(default=0,verbose_name="学习人数")
    fav_nums = models.IntegerField(default=0,verbose_name="收藏人数")
    image = models.ImageField(upload_to="courses/%Y/%m",verbose_name="封面地址")
    click_nums = models.IntegerField(default=0,verbose_name="点击人数")
    add_time = models.DateTimeField(default=datetime.now,verbose_name="添加时间")

    # 后期如果增加外键 需要设置外键可以为空
    teacher = models.ForeignKey(Teacher,on_delete=models.CASCADE,verbose_name="课程教师",null=True,blank=True)
    need_know = models.CharField(default="",max_length=150)
    whatToSay = models.CharField(default="",max_length=150)


    class Meta:
        verbose_name = "课程"
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.name

        # 获取资源信息

    def get_resources(self):
        return self.courseresource_set.all()

        # def get_teacher(self):
        #     return
        # # 获取相应教育机构的课程数目
        # def get_course_num(self):
        #     return self.course_org.courses

        # 教育机构的教师数目

        # 获取章节数目

    def get_part_num(self):
        return self.lesson_set.all().count()

        # 获取学习这门课程的所有学生人数

    def get_stu_count(self):
        return self.usercourse_set.all()[:3]  # 暂且获取三个学生信息

        # 获取相应课程的章节信息

    def get_chapter_count(self):
        return self.lesson_set.all()

class Lesson(models.Model):
    course = models.ForeignKey(Course,verbose_name="课程",on_delete=models.CASCADE)
    name = models.CharField(max_length=100,verbose_name="章节名称")
    add_time = models.DateTimeField(default=datetime.now,verbose_name="添加时间")

    class Meta:
        verbose_name = "章节"
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.name


    # 获取本章节下面的所有课程连接
    def get_all_video(self):
        return self.video_set.all()


class Video(models.Model):
    lesson = models.ForeignKey(Lesson,verbose_name="章节",on_delete=models.CASCADE)
    name = models.CharField(max_length=100, verbose_name="视频名称")
    course_time = models.IntegerField(default=0, verbose_name="视频长度")
    watch = models.CharField(default='',verbose_name="观看地址",max_length=200)
    add_time = models.DateTimeField(default=datetime.now, verbose_name="添加时间")

    class Meta:
        verbose_name = "视频"
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.name


class CourseResource(models.Model):
    # on_delete = CASCADE  允许级联删除操作
    course = models.ForeignKey(Course,verbose_name="课程",on_delete=models.CASCADE)
    name = models.CharField(max_length=100,verbose_name="资源名称")
    download = models.FileField(upload_to="course/resource/%Y/%m",verbose_name="资源地址",max_length=100)
    add_time = models.DateTimeField(default=datetime.now,verbose_name="添加时间")

    class Meta:
        verbose_name = "资源信息"
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.name