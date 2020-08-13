# 构建

1. install Maven-Copyer 到本地仓库

   https://github.com/julyss2019/Maven-Copyer

2. install JulyLibrary 到本地仓库

   https://github.com/julyss2019/JulyLibrary

3. 修改 pom.xml

   ```
               <plugin>
                   <groupId>com.github.julyss2019.maven.plugins</groupId>
                   <artifactId>copyer-plugin</artifactId>
                   <version>1.0.0</version>
                   <executions>
                       <execution>
                           <goals>
                               <goal>os-copy</goal>
                           </goals>
                           <configuration>
                               <osCopyers>
                                   <osCopyer>
                                       <source>${build.directory}/${build.finalName}.jar</source>
                                       <operatingSystems>
                                           <operatingSystem>
                                               <name>Mac OS X</name>
                                               <dests>
                                                   <dest>/Users/july_ss/Desktop/july_ss/mc_servers/1.15.2_paper/plugins/${artifactId}.jar</dest>
                                               </dests>
                                               <overwrite>true</overwrite>
                                           </operatingSystem>
                                           <operatingSystem>
                                               <name>Windows 10</name>
                                               <dests>
                                                   <dest>G:\july_ss\mc_servers\1.15.2_paper\plugins\${artifactId}.jar</dest>
                                               </dests>
                                               <overwrite>true</overwrite>
                                           </operatingSystem>
                                       </operatingSystems>
                                   </osCopyer>
                               </osCopyers>
                           </configuration>
                       </execution>
                   </executions>
               </plugin>
   ```

   将 dest 改为服务端的路径。

4. mvn clean packge