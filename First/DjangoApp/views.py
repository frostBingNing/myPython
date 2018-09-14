from django.shortcuts import render
from django.views import View
from django.http import Http404,HttpResponseRedirect
from django.urls import reverse

#  这里这两个方法作用就是 用来网页的重定向

# Create your views here.
# from django.shortcuts import render

from DjangoApp.models import Test

def index(request):
    articles = Test.objects.all() # 获取所有的文章
    return render(request, "index.html",{'articles':articles})

def show_article(request,article_id):
    article = Test.objects.get(pk=article_id)
    return render(request,"show_page.html",{'article':article})
# class IndexView(View):
#     def get(self, request):
#         return render(request, "index.html")


def edit_page(request,article_id):
    #如果是新建页面传递过来的  那么里面没有数据
    if str(article_id) == '0':
        return render(request,"edit_page.html")
    #如果是文章修改页面传递进来的，那么需要加上原先文章的内容
    else:
        # 把文章里面的数据传递进去
        article = Test.objects.get(pk=article_id)
        return render(request,"edit_page.html",{'article':article})


def submit_infor(request):

    title = request.POST['name']
    content = request.POST['content']
    article_id = request.POST['article_id']

    #  网页上面提交的数据在这里统一化格式方便处理
    if str(article_id) == '0':
        if title!=None and content!=None:
            Test.objects.create(name=title,content=content)
        # 提交数据库
        articles = Test.objects.all()  # 获取所有的文章
        # 再次返回主页面
        # return render(request, "index.html", {'articles': articles})
        # 重定向问题里面 主要的是 reverse函数
        #  using the named URL
        # reverse('news-archive') 就是本问题的解决方法
        return HttpResponseRedirect(reverse("index"))

    else:
        # 在这里尝试着处理 404 的错误
        try:
            text = Test.objects.get(pk=article_id)
        except Test.DoesNotExist:
        # text = Test.objects.get(pk=article_id)
            raise Http404("   这个界面暂时找寻不到   ")
        text.name = title
        text.content = content
        text.save()
        return render(request,"show_page.html",{'article':text})
