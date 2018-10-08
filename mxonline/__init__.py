

# 在主页面跳转功能制作的时候  view 可以传递给后台一个数据 current_page  用来鉴别当前栏目
# 然后在后台 采用  request.path|slice:'字节数 整数'    --- >  if xx = yy  active  选中被点击的页面