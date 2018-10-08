from datetime import datetime

from django.db import models
from django.contrib.auth.models import  AbstractUser
# Create your models here.
# 在这里制定用户需要的数据表，可以添加新的字段进去


# extend 原先的数据表字段
class UserProfile(AbstractUser):
    nickname = models.CharField(max_length=50,verbose_name="姓名",default="")
    birday = models.DateField(verbose_name="生日",null=True,blank=True)
    gender = models.CharField(max_length=10,choices=(('male','男'),('female','女')),default="男")
    address = models.CharField(max_length=500,default="")
    mobile = models.CharField(max_length=11,null=True,blank=True)
    #image 字段里面的upload 上传头像到该文件
    #ERRORS:users.UserProfile.image: (fields.E202) ImageField's 'upload_to' argument must be a relative path, not an absolute path.HINT: Remove the leading slash.
    image = models.ImageField(upload_to="image/%Y/%m",default="image/default.png",max_length=100)

    class Meta:
        verbose_name = "用户信息"
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.username

    def get_message_nums(self):
        # 使用的时候才调用
        from apps.operation.models import UserMessage

        return UserMessage.objects.filter(user=self.id,has_read=False).count()
# Create your models here.


# 在这里继续添加和user 有关的mode
# 邮箱验证model
class EmailVerifyRecord(models.Model):
    code = models.CharField(max_length=20,verbose_name="验证码")
    email = models.EmailField(max_length=50,verbose_name="邮箱")
    send_type = models.CharField(choices=(('register','注册'),('forget','密码找回'),('update_email','更新邮箱')),max_length=30,verbose_name="发送方式")
    send_time = models.TimeField(default=datetime.now,verbose_name="添加时间")

    class Meta:
        verbose_name = "邮箱验证码"
        verbose_name_plural = verbose_name



# 轮播图model
class Banner(models.Model):
    title =  models.CharField(max_length=100,verbose_name="标题")
    url =  models.URLField(max_length=200,verbose_name="轮播图地址")
    image = models.ImageField(upload_to="banner/%Y/%m",verbose_name="图片")
    index = models.IntegerField(default=100,verbose_name="顺序")
    add_time = models.DateTimeField(default=datetime.now,verbose_name="添加时间")

    class Meta:
        verbose_name = "轮播图"
        verbose_name_plural = verbose_name