from django.contrib import admin


# Register your models here.

# from models import Test 这种导入包的方式会产生错误  需要使用相对的路径
from .models import Test


# 这样就可以使用admin管理后台的数据 然后首先面对的问题就是每篇文章的默认标题都是Test （class） object
# 同时管理多个表单，但是每次改动都需要进行相应的注册

# admin.site.register(Email, ContactAdmin)

# 在这里定制自己的admin
class TestAdmin(admin.ModelAdmin):
    list_display = ('name','content')
    list_filter = ('name',)
admin.site.register(Test,TestAdmin)