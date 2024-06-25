public class MyExecutorService {

    static MyFixedThreadPool executor = new MyFixedThreadPool(2);

    public static void main(String[] args) {

        Runnable runnable = () -> {
            System.out.println("Task Started " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Task Finished " + Thread.currentThread().getName());
        };

        for (int i = 0; i < 5; i++) {
            executor.execute(runnable);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        executor.shutdown();
    }
}