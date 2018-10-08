from datetime import datetime

from django.db import models

# Create your models here.
class CityDict(models.Model):
    name = models.CharField(max_length=20,verbose_name="城市名")
    desc = models.CharField(max_length=200,verbose_name="描述")
    add_time = models.DateTimeField(default=datetime.now,verbose_name="添加时间")

    class Meta:
        verbose_name = "城市"
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.name

        # def __unicode__(self):
    #     return self.name
    # def str(self):
    #     return self.name

class CourseOrg(models.Model):
    name = models.CharField(max_length=50, verbose_name="机构名称")
    desc = models.TextField(verbose_name="机构描述")
    company_type = models.CharField(default="pxjg",verbose_name="机构类别",choices=(('pxjg','培训机构'),('gx','高校'),('gr','个人')),max_length=20)
    click_nums = models.IntegerField(default=0, verbose_name="点击人数")
    fav_nums = models.IntegerField(default=0, verbose_name="收藏人数")
    image = models.ImageField(upload_to="org/%Y/%m", verbose_name="logo")
    address = models.CharField(max_length=150, verbose_name="机构地址")
    students = models.IntegerField(default=0, verbose_name="学生人数")
    courses = models.IntegerField(default=0, verbose_name="课程数量")
    tag = models.CharField(default="世界知名",max_length=10, verbose_name="机构标签")
    city = models.ForeignKey(CityDict,on_delete=models.CASCADE,verbose_name="所在城市")
    add_time = models.DateTimeField(default=datetime.now,verbose_name="添加时间")

    class Meta:
        verbose_name = "教育机构"
        verbose_name_plural =  verbose_name
        # raw_id_fields = ("city",)

    def __str__(self):
        return self.name

    def get_count_tea(self):
        return self.teacher_set.all().count()

class Teacher(models.Model):
    org = models.ForeignKey(CourseOrg,verbose_name="所属机构",on_delete=models.CASCADE)
    name = models.CharField(max_length=30, verbose_name="教师姓名")
    work_year =  models.IntegerField(default=0, verbose_name="工作年龄")
    work_company =models.CharField(max_length=50, verbose_name="就职公司")
    work_position = models.CharField(max_length=50, verbose_name="职位")
    points = models.CharField(max_length=50, verbose_name="教学特点")
    click_nums = models.IntegerField(default=0, verbose_name="点击人数")
    ages = models.IntegerField(default=23,verbose_name="教师年龄")
    fav_nums = models.IntegerField(default=0, verbose_name="收藏人数")
    image = models.ImageField(default='',upload_to="teacher/%Y/%m", verbose_name="封面地址")
    add_time = models.DateTimeField(default=datetime.now,verbose_name="添加时间")


    # 如果添加了下面的信息 那么输出就是备注的信息   ---- 教师信息
    class Meta:
        verbose_name = "教师信息"
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.name

    # 因为课程引用了教师  所以在这里采用了 主表_set 的方式
    def get_the_course(self):
        return self.course_set.all()

    # 返回数目
    def get_count(self):
        return self.course_set.all().count()