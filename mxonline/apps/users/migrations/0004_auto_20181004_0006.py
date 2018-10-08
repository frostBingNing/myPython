# Generated by Django 2.1.1 on 2018-10-04 00:06

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('users', '0003_auto_20181004_0002'),
    ]

    operations = [
        migrations.AlterField(
            model_name='emailverifyrecord',
            name='send_type',
            field=models.CharField(choices=[('register', '注册'), ('forget', '密码找回'), ('update_email', '更新邮箱')], max_length=30, verbose_name='发送方式'),
        ),
    ]
