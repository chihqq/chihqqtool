package io.github.chihqq.thread;

/**
 * Created by Administrator on 2022/12/13 is 22:08.
 *
 * @Description
 * @Author sznzn <sznzn614866450@outlook.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/12/13
 */

public class MyThread implements Runnable {

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void main(String[] args) {

        Thread thread = new Thread(new MyThread());
        thread.setName("MyThread");
        thread.setPriority(10);
        // 设置守护进程
        thread.setDaemon(true);
        thread.start();



        Thread.currentThread().setPriority(1);

        for (int i = 0; i < 4; i++) {
            System.out.println(Thread.currentThread().getName());

            try {
                if (i == 3) {
                    //thread.join();
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
