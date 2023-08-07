package com.xlm.mysqldemo.Completable;

import lombok.Data;

import java.util.concurrent.*;

/**
 * @description:
 * @author: Yihui Wang
 * @date: 2022年08月21日 11:48
 */
public class CompletableFutureDemo {
    public static ExecutorService executorService = new ThreadPoolExecutor(
            10,
            100,
            30L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static ArticleVO asyncReturn() {
        ArticleVO article = new ArticleVO();
        long startTime = System.currentTimeMillis();
        CompletableFuture<ArticleVO> articleContent = CompletableFuture.supplyAsync(() -> {
            try {
                article.setId(1L);
                article.setContent("我是宁在春写的文章内容");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return article;
        }, executorService);
        // 这里的res 就是第一个个 CompletableFuture 执行完返回的结果
        // 如果要测试它们的异步性，其实应该在下面的所有执行中，都让它们沉睡一会，这样效果会更加明显
        // executorService 是放到我们自己创建的线程池中运行
        CompletableFuture<Void> author = articleContent.thenAcceptAsync((res) -> {
            res.setAuthor(res.getId() + "的作者是宁在春");
        }, executorService);
        CompletableFuture<Void> articleComment = articleContent.thenAcceptAsync((res) -> {
            res.setComment(res.getId() + "的相关评论");
        }, executorService);
        CompletableFuture<Void> articleCategory = articleContent.thenAcceptAsync((res) -> {
            res.setCategory(res.getId() + "的分类信息");
        }, executorService);
        CompletableFuture<Void> articleColumn = articleContent.thenAcceptAsync((res) -> {
            res.setColumn(res.getId() + "的文章专栏信息");
        }, executorService);
        CompletableFuture<Void> recommend = articleContent.thenAcceptAsync((res) -> {
            res.setRecommend(res.getId() + "的文章推荐信息");
        }, executorService);

        CompletableFuture<Void> futureAll = CompletableFuture.allOf(author, articleComment, articleCategory, articleColumn, recommend);

        try {
            // get() 是一个阻塞式方法 这里是阻塞式等待所有结果返回
            // 因为要等待所有结果返回，才允许方法的结束，否则一些还在执行中，但是方法已经返回，就会造成一些错误。
            futureAll.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗费的总时间===>" + (endTime - startTime));

        // 所有任务执行完成后，将构建出来的视图结果进行返回
        return article;
    }

    public static void main(String[] args) {
        ArticleVO articleVO = asyncReturn();
        System.out.println(articleVO);
    }

}

@Data
class ArticleVO {
    private Long id;
    private String content;
    private String author;
    private String comment;
    private String category;
    private String column;
    private String recommend;
}
