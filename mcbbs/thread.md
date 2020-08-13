# JulySafe

## 为什么使用 JulySafe

* 开源
* 多版本支持
* 多服务端支持
* 高度自定义
* 功能齐全
* 人性化的交互方式

## 功能列表

### 优化类

#### 定时掉落物清理 & 定时生物清理

可以定时清理指定世界的指定生物和掉落物。且提供了多种过滤器。

```
clean_entity: # 实体清理
  # ...
  filter: # 过滤器
    water_mobs: true # 水生动物
    named: true # 已命名的怪物
    npcs: true # 村民，流浪商人
    animals: true # 动物
    golems: true # 雪傀儡，铁傀儡，潜影贝
    # 其他过滤
    types:
      - 'ENDER_DRAGON'
      - 'ZOMBIE_VILLAGER'
  # ...
clean_drop: # 清理掉落物
  # ...
  filter: # 过滤器
    lore_items: true # 带 lore的物品
    materials:
      - DIAMOND
      - BLACK_SHULKER_BOX
      - BLUE_SHULKER_BOX
      - BROWN_SHULKER_BOX
      - CYAN_SHULKER_BOX
      - GRAY_SHULKER_BOX
      - GREEN_SHULKER_BOX
      - LIGHT_BLUE_SHULKER_BOX
      - LIGHT_GRAY_SHULKER_BOX
      - LIME_SHULKER_BOX
      - MAGENTA_SHULKER_BOX
      - ORANGE_SHULKER_BOX
      - PINK_SHULKER_BOX
      - PURPLE_SHULKER_BOX
      - RED_SHULKER_BOX
      - SHULKER_BOX
      - WHITE_SHULKER_BOX
      - YELLOW_SHULKER_BOX
  # ...
```

采用人性化的交互方式，自带 BossBar 倒计时，倒计时不占用聊天屏幕影响玩家游戏体验：

![clean (2).gif](https://i.loli.net/2020/08/10/UCogR3AIYc6fLaM.gif)

#### 密集实体清理

> 纯净服的卡服的主要原因之一是因为存在大量密集实体，而这些实体通常是通过 刷怪笼、刷怪塔、猪人塔、鱼塔  产生的。
>
> 部分自动化装置使用了大量矿车堆叠来挤压怪物致死也会对服务器的性能造成较大影响。

当一个区块（16\*256\*16）范围内的实体超过配置文件限制时，插件就会自动清理。

![anti_mob_farm.gif](https://i.loli.net/2020/08/10/AFKnWBXIc14NZie.gif)

#### 生物出生间隔限制

该功能可以限制全服怪物的出生间隔，能削弱自动化刷怪装置的刷怪频率。

### 保护类

#### QuickShop 偷物品 Bug 修复

经过测试，目前大多数版本（包括 1.15）的 QuickShop 都存在漏斗或漏斗矿车偷物品 Bug。

该功能在不禁用漏斗和漏斗矿车的前提下修复了这个 Bug。

#### 红石限制

>  纯净服卡服的主要原因之一是因为存在大量红石机器。这些红石机器产生了大量的运算和方块更新（尤其是活塞）。
>
>  **目前市面上的反高频插件对简单的红石机器是有效的，但对使用了大量中继器的红石机器却起不了作用。**

本插件对区块红石的运行时间进行检测，一旦超过阈值则禁止使用该区块红石一段时间，不对红石机器进行破坏性操作。

![redstone_limit.gif](https://i.loli.net/2020/08/10/ZLJWiGOm6jxrzSF.gif)

![Snipaste_2020-08-11_17-32-25.png](https://i.loli.net/2020/08/11/4OaCPIV3QtTvi5N.png)

#### 非法玩家检测

该功能能在**一定程度上**防止通过 Bug 或 后门插件非法获取 OP 或创造模式。

一旦不在配置文件白名单的玩家获取了 OP 或创造模式就会自动封禁。

**该功能需要手动开启。**

#### 农田防踩踏

该功能可以防止玩家把种好的庄稼踩烂。

#### 聊天频率限制

该功能可以限制玩家的聊天频率，防止刷屏。

#### 聊天正则过滤

该功能可以使用正则表达式过滤玩家的聊天内容，防止玩家说脏话。

## 指令与权限

### 插件相关（JulySafe.admin）

#### /jsafe plugin reload
重载插件。

### 帮助者相关

#### /jsafe helper tps
查看服务器 tps。

#### /jsafe helper entities
查看服务器实体情况。

![Snipaste_2020-08-11_17-33-44.png](https://i.loli.net/2020/08/11/T9qmOAPZvHSRV7N.png)

## 开源

可自行构建。

https://github.com/julyss2019/JulySafe

##  下载

https://afdian.net/@july_ss
