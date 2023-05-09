# cc-mock

#### 介绍

cc-mock是一款的智能mock工具。支持springboot项目下的自动生成接口响应数据。方便在接口开发完成之前，前端联调数据时使用。

#### 软件架构

软件架构说明

#### 安装教程

1. 引入依赖
   ```xml
    <dependency>
        <groupId>com.borened.mock</groupId>
        <artifactId>cc-mock-api-starter</artifactId>
    </dependency>
   ```
2. 配置mock启用（可选），默认情况下引入依赖后自动配置即启用了mock，开发完毕后需关闭mock。
    ```yml
   ccmock:
      enabled: true
   ```

#### 使用说明

- 开发接口

  ```java
  /**
   * 泛型参数响应
   */
  @GetMapping("/test2")
  public MyResult<Foo> test2(){
      //这里可以是自定义逻辑,可以从数据库或其他地方读取
      Foo data = new Foo();
      return new MyResult<>(data);
  }
  ```

  

- 启动项目,访问http://localhost:8080/test2, 可以得到mock结果。

  ```json
  {
  
      "message": "yes",
      "status": "0000000",
          "data": {
          "account": 339.07,
          "age": 681,
          "attrs": [
              {
                  "age": 775,
                  "gender": 379,
                  "name": "架殃桅因蕊险稿刷裕隘"
              },
              {
                  "age": 343,
                  "gender": 4,
                  "name": "夏恍握滇势搓疲泣酉有"
              }
          ],
          "createTime": "1985-09-17 03:52:16",
          "gender": 429,
          "list": [
              "丧冒橇抿诈亡穆樱挺签",
              "娄炉撒盖岂狱彝弧仆弹",
              "锯中应孽榜挂确含狭硷"
          ],
          "map": {
              "棠那苯鳃而廷膊潘古由": 994.50,
              "伐园覆低巧沾萤大性泡": 566.98,
              "泄链宙诗寅仙漏莽击腊": 235.57,
              "凳鸣疏痰绽棉钒银蛔扁": 481.40
          },
          "nickName": "址逃虾霄憨椽苑职貌犀",
          "password": "戳狈单兢杆汪创型慷豁",
          "username": "押相缸冷嫉恶捣卧块射"
      }
  }
  ```

- [springboot集成cc-mock的案例。](https://gitee.com/boren07/cc-mock/tree/master/cc-mock-samples/spring-boot-sample)





#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

#### 优点

- 无侵入：cc-mock在现有springboot框架的基础上做mock增强，对现有代码和架构不会产生任何影响。
- 使用简单无损耗：几乎只需要一个配置即可开启和关闭MOCK功能，有大量的自动mock策略。关闭mock后无任何性能损耗。
- 易扩展：支持自定义mock策略，自定义Mock响应结构。

#### 版本更新说明

1.0.0：第一个正式发布版本