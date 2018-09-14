from django.db import models

# Create your models here.
# 模板里面的数据
class Test(models.Model):
    # 创建字段
    name = models.CharField(max_length=30) # 标题的默认长度不超过该数值
    content = models.TextField(null=True) # 初始化内容为空


    # 因为每篇文章都是一个对象 所以可以在这里进行相应的处理，来确保后台保存正确的标题数据，而不是默认的标题
    def __str__(self):
        return self.name




# # 在这里可以进行多个模型的管理操作
# class Email(models.Model):
#     email = models.EmailField()
#     # age = models.CharField(max_length=20)
#     def __str__(self):
#         return self.email
#
# class ClassMates(models.Model):
#     # 外部键
#     email = models.ForeignKey(Email,None)
#     name = models.CharField(max_length=20)
#     def __str__(self):
#         return self.name