# -*- coding: utf-8 -*-

from scrapy.cmdline import execute
#   cmd execute



#   加上这个文件  就可在pycharm 里面调试scrapy 了
# print(os.path.dirname(os.path.abspath(__file__)))  # main.py 所在文件的主目录
# execute(['scrapy','crawl','jobbole'])
execute("scrapy crawl jobbole".split())