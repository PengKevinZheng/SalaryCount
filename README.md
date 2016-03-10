# SalaryCount

      美团    3-5年经验   15-30k  北京    【够牛就来】hadoop高级工程...
      北信源  3-5年经验   15-20k  北京    Java高级工程师（有Hadoo...
      蘑菇街  3-5年经验   10-24k   杭州    hadoop开发工程师
      晶赞科技    1-3年经验   10-30k  上海    hadoop研发工程师
      秒针系统    3-5年经验   10-20k  北京    hadoop开发工程师
      搜狐    1-3年经验   18-23k  北京    大数据平台开发工程师（Hadoo...
      执御    1-3年经验   8-14k   杭州    hadoop工程师
      KK唱响  3-5年经验   15-30k  杭州    高级hadoop开发工程师
      晶赞科技    1-3年经验   12-30k  上海    高级数据分析师（hadoop）
      亿玛在线（北京）科技有限公司    3-5年经验    18-30k  北京    hadoop工程师
      酷讯    1-3年经验   10-20k  北京    hadoop Engineer/...
      游族网络    5-10年经验  20-35k  上海    hadoop研发工程师
      易车公司    3-5年经验   15-30k  北京    hadoop工程师
      爱图购  1-3年经验   8-15k   杭州    hadoop开发工程师
      晶赞科技    3-5年经验   15-33k  上海    hadoop研发工程师
      
      基于这份数据，我们用Hadoop程序统计一下各工作经验段的Hadoop工程师薪资上下限.
      
      1. map阶段
      
        map输入key：每行偏移量    IntWritable 
      
        map输入value： 每行数据    Text
      
        map输出key： 工作年限      Text
      
        map输出value：工资范围     Text    
        
      2. reduce阶段
      
        reduce输入key：工作年限   Text
        
        reduce输入value：工资范围  Iterable<Text>
        
        reduce输出key：  工作年限  Text
        
        reduce输出value：最低工资+最高工资  Text
        
        得到最低（高）用的方法是：设置一个临时变量为0，遍历数组与每一个元素比较，始终将交小（大）的值赋值给临时变量。遍历之后得到的就是最小（大）值。  
        
        
