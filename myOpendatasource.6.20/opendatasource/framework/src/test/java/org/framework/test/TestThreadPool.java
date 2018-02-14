package org.framework.test;

import java.util.Random;

import org.junit.Test;

import com.onezetta.threadpool.MyThreadPool;
import com.onezetta.threadpool.TaskBaseImpl;

import junit.framework.TestCase;

/**  
 *   
 * Test  
 * 测试类  
 * @author yangchuang  
 * @since 2014-3-15 上午11:03:23    
 * @version 1.0.0  
 * 修改记录
 * 修改日期    修改人    修改后版本    修改内容
 */
public class TestThreadPool extends TestCase{
	
	@Test
    public void testThreadPool() throws Exception {
        MyThreadPool pool = new MyThreadPool(50, 20, 9);
        final Random r = new Random();
        pool.execute();
        for (int i = 0; i < 10; i++) {
            final int l = i;
            pool.addTask(new TaskBaseImpl () {
                
                @Override
                public int getMaxAgainExecuteNum() {
                    return 1;
                };
                @Override
                public void stratWork() throws Exception {
                    int time = (int) (1000 * (1 + r.nextFloat()));
                    System.out.println(l + "---Start" + time);
                    Thread.sleep(time);
                    try{
                        int k=1/0;
                    }catch(Exception e){
                        throw new Exception(l+"/0");
                    }
                    System.out.println(l + "---End" + time);
                }
            });
            
        }
    }
}
