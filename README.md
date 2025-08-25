## Kernel介绍

Kernel是由灵码科技技术负责人”明天的地平线“提供的一套通用的Java底层通用框架，定义和封装了常用的工具和实体模型，同时对SpringCloud Alibaba框架的jar包版本做了约束，
解决开发者各种依赖冲突的问题

## Kernel存在的意义



## 快速开始



## 项目介绍
| 项目                        | 项目描述                                     |
|---------------------------| ---------------------------------------------- |
| kernel-core               | 主要负责核心模型定义，放与具体框架无关的东西 |
| kernel-common              | 主要负责各类工具类                           |
| kernel-dependencies        | 主要负责定义和约束依赖包                           |
| kernel-boot-starter-parent | 定义starter的父pom，统一管理和约束starter的版本，统一的打包构建管理                           |


## Kernel与SpringBoot的对应关系
Kernel完成了与SpringBoot各个版本的适配，避免开发者自行适配过程中要解决各种不同的兼容性问题，具体对应关系如下：
| Kernel版本 | SpringBoot版本 |
|----------|--------------|
| 1.x.x    | 2.x.x        |
| 3.x.x    | 3.x.x        |