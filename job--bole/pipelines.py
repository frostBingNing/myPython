# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html


#   接下来学习定制自己的  pipelines
from scrapy.pipelines.images import  ImagesPipeline
import codecs
import json
from scrapy.exporters import JsonItemExporter
import pymysql  #  python 3.6
from twisted.enterprise import adbapi  # 异步方式存储



# TextPipeline 就是和txt 文件进行交互
# 优先级 300
class TextPipeline(object):
    def process_item(self,item,spider):
        f = open('e://bole/' + (item.get('title')) + '.txt', "w")
        # print(item.get('title'))
        # print(type(item.get('title')))
        f.write((item.get('title')) + '\n')
        f.write((item.get('create_time')) + '\n')
        f.write((item.get('tag')) + '\n' + (item.get('url_id')) + '\n')
        f.write((item.get('front_local')) + '-----END-----' )
        # f.write((item.get('front_image_pic')) + '\n' + '-------END------')
        f.close()
        return item

# 将数据保存在 json 文件里面
#  first way
class DownloadJson(object):
    def __init__(self):
        self.file = codecs.open('my json.json','w', encoding= 'utf-8')
    def process_item(self,item,spider):
        information =  json.dumps(dict(item),ensure_ascii=False) + '\n'
        self.file.write(information)
        return item
    # spider 信号量  来结束
    def spider_closed(self,spider):
        self.file.close()

# second way  保存方式为 列表[] 源代码的开始结束分别加了 [ ]
class JsonOffcial(object):
    def __init__(self):
        self.file = open('second json.json','wb')
        self.expotter = JsonItemExporter(self.file,encoding='utf-8',ensure_ascii=False)
        self.expotter.start_exporting()
    def close_spider(self,spider):
        self.expotter.finish_exporting()
        self.file.close()
    def process_item(self,item,spider):
        self.expotter.export_item(item)

#  继承  把一个函数的功能更新了
# 优先级 1  确保数据正确添加
class AddPictures(ImagesPipeline):
    def item_completed(self, results, item, info):
        if 'front_local' in item:  #  健壮性
            for ok ,values  in  results:
                file_image = values['path']
            item['front_local'] = file_image
        return item

#  最为正常的链接数据库  并保存数据的class
class SaveToMysql(object):
    def __init__(self):
        #  python 3.6  里面 连接数据库 是  pymysql  后面不加 charset  ensure_unicode = False
        self.coon= pymysql.connect(host = "localhost" ,database = 'tnt',user = 'root',password='123456')
        self.cur = self.coon.cursor()
        # self.coon  就是建立一个连接数据库的状态
        # self.cur   执行相关的SQL语句
    def process_item(self,item,spider):
        insert_to ="insert into  bole(title,create_time,url_id,tag) values(%s ,%s,%s,%s)"
        # print(type(item['title']))
        # 切记需要 进行一次 转码   转化成 utf-8
        self.cur.execute(insert_to,(item['title'].encode('utf-8'),item['create_time'].encode('utf-8'),item['url_id'].encode('utf-8'),item['tag'].encode('utf-8')))
        # 提交事务
        self.coon.commit()

## --------------分界线----------------###
#  twisted enterprise  异步加载的方式  减少程序的负担
class MysqlTwistConn(object):
    def __init__(self,dbpool):
        self.dbpool = dbpool


    @classmethod
    def from_settings(cls, settings):
        dbparms = dict(
            host = settings["MYSQL_HOST"],
            db = settings["MYSQL_DATABASE"],
            user = settings["MYSQL_USER"],
            passwd = settings["MYSQL_PASSWORD"],
            cursorclass=pymysql.cursors.Cursor,
        )
        dbpool = adbapi.ConnectionPool("pymysql", **dbparms)

        return cls(dbpool)

    def process_item(self, item, spider):
        #使用twisted将mysql插入变成异步执行
        query = self.dbpool.runInteraction(self.do_insert, item)
        query.addErrback(self.handle_error, item, spider) #处理异常

    def handle_error(self, failure, item, spider):
        # 处理异步插入的异常
        print (failure)

    def do_insert(self, cursor, item):
        insert_to = "insert into  bole(title,create_time,url_id,tag) values(%s ,%s,%s,%s)"
        # print(type(item['title']))
        # 切记需要 进行一次 转码   转化成 utf-8
        cursor.execute(insert_to, (
        item['title'].encode('utf-8'), item['create_time'].encode('utf-8'), item['url_id'].encode('utf-8'),
        item['tag'].encode('utf-8')))


