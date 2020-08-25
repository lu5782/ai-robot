FROM centos #基于centos镜像进行构建
EXPOSE 8888:8080 #将docker中的8080端口映射到宿主机的8888端口
ADD jdk /usr/local/ #将准备好的jdk包添加到容器中的指定位置
ENV JAVA_HOME /usr/local/jdk #配置JAVA_HOME的环境变量
ENV PATH $PATH:$JAVA_HOME/bin #配置JAVA_HOME配置到PATH的环境变量中
ADD test.jar /home/  #将springboot的jar包添加到容器的指定位置中
RUN /bin/bash -c 'source /etc/profile; echo $JAVA_HOME' #执行命令使环境变量生效
WORKDIR /home #更换工作路径
ENTRYPOINT ["java", "-jar", "test.jar"] #容器启动后运行jar包