package com.hadoop.salary;

import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


/**
 * ��������������Hadoop����ʦн��ͳ�ƣ�������������޶ε�нˮ��Χ
 */
public class GongziCount extends Configured implements Tool {

    public static class GongziMapper extends Mapper< LongWritable, Text, Text, Text> {
         public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
             //ʾ�����ݣ�����  3-5�꾭��  15-30k ����   hadoop�߼�����
             String line = value.toString();//��ȡÿ������

 String[] record = line.split( "\\s+");//ʹ�ÿո������������
 //key=record[1]�����3-5�꾭��
 //value=record[2]��15-30k
 //��ΪMapper��������� Reduce ��

 if(record.length >= 3){

    context.write( new Text(record[1]), new Text(record[2]) );

 }
        }
    }
    public static class GongziReducer extends Reducer< Text, Text, Text, Text> {
         public void reduce(Text Key, Iterable< Text> Values, Context context) throws IOException, InterruptedException {

        	 int low = 0;//��¼��͹���
        	 int high = 0;//��¼��߹���
        	 int count = 1;
        	 //���ͬһ���������ޣ�key����ѭ��н�ʼ��ϣ�values���������valueֵ��ͳ�Ƴ���͹���low����߹���high
        	 for (Text value : Values) {
     String[] arr = value.toString().split("-");
     int l = filterSalary(arr[0]);
     int h = filterSalary(arr[1]);
     if(count==1 || l< low){
         low = l;
     }
     if(count==1 || h>high){
         high = h;
     }
     count++;
 }
 context.write(Key, new Text(low + "-" +high + "k"));

        }
    }
	//������ʽ��ȡ����ֵ
    public static int filterSalary(String salary) {
        String sal = Pattern.compile("[^0-9]").matcher(salary).replaceAll("");
        return Integer.parseInt(sal);
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();//��ȡ�����ļ�

		Path out = new Path(args[1]);
        FileSystem hdfs = out.getFileSystem(conf);
        if (hdfs.isDirectory(out)) {//ɾ���Ѿ����ڵ����Ŀ¼
            hdfs.delete(out, true);
        }

        Job job = new Job(conf, "GongziCount" );//�½�һ������
        job.setJarByClass(GongziCount.class);// ����

        FileInputFormat.addInputPath(job, new Path(args[0]));// �ļ�����·��
        FileOutputFormat.setOutputPath(job, new Path(args[1]));// �ļ����·��

        job.setMapperClass(GongziMapper.class);// Mapper
        job.setReducerClass(GongziReducer.class);// Reducer

        job.setOutputKeyClass(Text.class);//������key����
	job.setOutputValueClass(Text.class);//��������value����


	return job.waitForCompletion(true) ? 0 : 1;//�ȴ�����˳���ҵ
    }


    /**
     * @param args �����ļ������·��������Eclipse��Run Configurations����Arguments���磺
     * hdfs://djt002:9000/junior/salary.txt
     * hdfs://djt002:9000/junior/salary-out/
     */
    public static void main(String[] args) throws Exception {
        try {
		//��������·�������·��
		String[] args0 = {
				"hdfs://192.168.10.11:9000/dajiangtai/gongzi.txt.utf8",
                "hdfs://192.168.10.11:9000/dajiangtai/gongzi_self-out/"
		};
        	int res = ToolRunner.run(new Configuration(), new GongziCount(), args0);
        	System.exit(res);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}