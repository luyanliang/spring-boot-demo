#说明
本项目为代码生成项目，自动生成mp相关的实体，DAO，SERVICE，CONTROLLER及部分通用方法。

## 使用jar
如果使用jar，将yml文件拷出并配置好，使用
> java -jar common-code-generator.jar --spring.config.location=配置文件位置

进行代码生成，由于有内部配置，缺省配置将使用内部配置文件配置。

## 使用本项目
在test目录下配置好输出文件位置，使用单元测试方法进行模板生成。
