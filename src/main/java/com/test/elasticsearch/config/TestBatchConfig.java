package com.test.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j // log 사용을 위한 lombok 어노테이션
@Configuration // spring batch의 모든 Job은 해당 어노테이션은 @Configuration으로 등록
public class TestBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public TestBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job testJob() {
        return jobBuilderFactory.get("testJob") // "testJob"이라는 BatchJob 생성
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("TestJobTaskletStep").tasklet( // TestJobTaskletStep이라는 BatchStep 생성
                (StepContribution contribution, ChunkContext chunkContext) -> {
                    log.info("==== Tasklet called ====");

                    for (int i = 0; i < 100; i++) {
                        log.info("testJob Batch Log {}", i);
                    }
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
