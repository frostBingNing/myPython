# -*- coding: utf-8 -*-
import scrapy
from urllib import parse
from bole.items  import BoleItem
import re
from bole.utlies.common import get_md5
from scrapy.loader import ItemLoader


class JobboleSpider(scrapy.Spider):
    name = 'jobbole'
    allowed_domains = ['jobbole.com']
    # start_urls =  ****** all-posts/page/XXX
    start_urls = ['http://blog.jobbole.com/all-posts/']


    def parse(self, response):
        # 获取当前页面的所有url
        lists = response.xpath('//div[@class="post floated-thumb"]/div/a').extract()
        all_pictures =  response.xpath('//div[@class="post floated-thumb"]/div/a/img').extract()
        targets = []
        targets_pic = []
        for i in lists:
            target_url = re.findall('(http://blog.jobbole.com/\d{1,6})',i)
            # href = "http://blog.jobbole.com/112098/"
            # print("当前网址是 " + target_url[0])
            targets.append(target_url[0])
        for j in all_pictures:
            # print(j)
            #  这里的这个正则使用了 介于src 以及 alt  的这种思路 然后就可以正确的找到答案了  正则牛逼
            if re.findall('src=\"(http://.*[jpg,png]).*alt',j) == True:
                pic_target = re.findall('src=\"(http://.*[jpg,png]).*alt',j)
                # print("配套图片url为" + pic_target[0])
                # targets.append(target_url[0])
                targets_pic.append(pic_target[0])
            else:
                pic_target =  re.findall('src=\"(.*[jpg,png]).*alt',j)
                # print(pic_target[0])
                targets_pic.append(pic_target[0])
        for i in range(len(lists)):
            #response.urljoin()  可以正确的组建 后面没有域名的网址
            # 不知道为什么后面的 需要重新写一下？？？？
            yield scrapy.Request(url=parse.urljoin(response.url,targets[i]), meta = {'front_image_pic': parse.urljoin(response.url,targets_pic[i])},callback=self.detiles)

        # 提取下一页  并提交scrapy 下载
        # 在这里 并不是每个页面都有下一页的url 所以直接判断是否为真值
        # if response.xpath('//a[@class="next page-numbers"]').extract() :
        #     next_url = response.xpath('//a[@class="next page-numbers"]').extract()[0]
        #     true_url = re.findall('(http://blog.jobbole.com/all-posts/page/\d*)',next_url)
        #     if true_url[0] != None:
        #         # 测试的时候打开网址
        #         # print(true_url[0])
        #         yield scrapy.Request(url = true_url[0],callback = self.parse)


    def detiles(self,response):
            dict = BoleItem()
            # 在这里  每个地方的进入id 都是文章的id  所以用了  entry-header
            front_image_pic =  response.meta.get('front_image_pic',"")
            # print(front_image_pic)
            dict['front_image_pic'] = [front_image_pic]
            # print(dict['front_image_pic'])
            # response.url  就是当前访问的网址    感觉类似于  requests.response 里面的那些内容
            dict['url_id'] = get_md5(response.url)
            dict['title'] = response.xpath('//div[@class="entry-header"]/h1/text()').extract()[0]
            dict['create_time'] = response.xpath('//p[@class="entry-meta-hide-on-mobile"]/text()').extract()[0].strip().replace('·','').strip()
            dict['tag'] = response.xpath('//p[@class="entry-meta-hide-on-mobile"]/a/text()').extract()[0]
            # entry = response.xpath('//div[@class="entry"]').extract()

            # 采用 ItemLoader 的方法   不报错
            item_loader = ItemLoader(item=BoleItem(),response=response)
            item_loader.add_value('front_image_pic',response.meta.get('front_image_pic'))
            item_loader.add_value('url_id',get_md5(response.url))
            item_loader.add_xpath('title','//div[@class="entry-header"]/h1/text()')
            item_loader.add_xpath('create_time','//p[@class="entry-meta-hide-on-mobile"]/text()')
            item_loader.add_xpath('tag','//p[@class="entry-meta-hide-on-mobile"]/a/text()')
            #  添加完毕
            #  进行解析  生成item对象
            bole_dict = item_loader.load_item()

            yield bole_dict