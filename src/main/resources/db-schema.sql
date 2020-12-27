CREATE SCHEMA IF NOT EXISTS MONITOR;

SET SCHEMA MONITOR;

CREATE TABLE IF NOT EXISTS ITEM_TYPE (ID INT NOT NULL PRIMARY KEY, 
                                      NAME VARCHAR(50) NOT NULL UNIQUE);

CREATE TABLE IF NOT EXISTS SENSOR (ID INT NOT NULL PRIMARY KEY, 
                                   NODE VARCHAR(15) NOT NULL, 
                                   NAME VARCHAR(50) NOT NULL, 
                                   ITEM_TYPE INT NOT NULL,                                    
                                   VALUE double, 
                                   UNIT VARCHAR(5), 
                                   TIME TIMESTAMP, 
                                   TOPIC VARCHAR(50));
                                   
ALTER TABLE IF EXISTS SENSOR ADD FOREIGN KEY (ITEM_TYPE) REFERENCES ITEM_TYPE (ID);                               

CREATE TABLE IF NOT EXISTS SWITCH (ID INT NOT NULL PRIMARY KEY, 
                                   NODE VARCHAR(15) NOT NULL, 
                                   NAME VARCHAR(50) NOT NULL, 
                                   ITEM_TYPE INT NOT NULL,                                    
                                   VALUE INT, 
                                   UNIT VARCHAR(5), 
                                   TIME TIMESTAMP, 
                                   TOPIC VARCHAR(50));
                                   
ALTER TABLE IF EXISTS SWITCH ADD FOREIGN KEY (ITEM_TYPE) REFERENCES ITEM_TYPE (ID);                                        
                                                                     
  
*/                                        





