# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy
#   这里的scrapy.Field(input_processor= , )
from scrapy.loader.processors import MapCompose,TakeFirst,Join
# from scrapy.loader.processors import TakeFirst
# 在这里  分别是  1： 自定义的函数处理  2：去首个  3： 连接

def theTime(value):
    temp_time1 = value.strip().replace('·','').strip()
    return temp_time1


def return_value(value):
    return value

class BoleItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    # 标题
    title = scrapy.Field()
    # 文章发表日期
    create_time = scrapy.Field(
        input_processor = MapCompose(theTime) , # 调用theTime函数
        output_processor = TakeFirst()   #  从 the_time1 里面提取第一个元素
    )
    # 网址id  hash id
    url_id = scrapy.Field()
    # 标签
    tag = scrapy.Field(
        output_processor = Join("--")
    )
    # 封面图片
    front_local = scrapy.Field()  # 图片在本地的路径
    #  封面图片url
    front_image_pic = scrapy.Field(
        output_processor= MapCompose(return_value)
    )