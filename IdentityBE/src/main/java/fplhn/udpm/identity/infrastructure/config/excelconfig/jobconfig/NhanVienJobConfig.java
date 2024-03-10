package fplhn.udpm.identity.infrastructure.config.excelconfig.jobconfig;

import fplhn.udpm.identity.infrastructure.config.excelconfig.commonio.NhanVienProcessor;
import fplhn.udpm.identity.infrastructure.config.excelconfig.commonio.NhanVienRowMapper;
import fplhn.udpm.identity.infrastructure.config.excelconfig.commonio.NhanVienWriter;
import fplhn.udpm.identity.infrastructure.config.excelconfig.model.dto.TransferRequestExcel;
import fplhn.udpm.identity.infrastructure.config.excelconfig.model.request.ImportExcelRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;

@Configuration
@Slf4j
@EnableTransactionManagement
public class NhanVienJobConfig {

    @Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager;


    @Value("${EXCEL_FOLDER}")
    private String pathSaveExcel;

    @Bean
    @StepScope
    ItemReader<ImportExcelRequest> excelNhanVienReader(@Value("#{jobParameters['fullPathFileName']}") String nameFile) {
        try {
            PoiItemReader<ImportExcelRequest> reader = new PoiItemReader<>();
            Resource resource = new FileSystemResource(new File(pathSaveExcel + nameFile));
            if (!resource.exists()) {
                throw new RuntimeException("Could not read the file!");
            }
            log.info("RESOURCES: " + resource.getURI());
            reader.setResource(resource);
            reader.setLinesToSkip(1);
            reader.open(new ExecutionContext());
            reader.setRowMapper(rowMapper());
            reader.setLinesToSkip(1);
            return reader;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @StepScope
    private RowMapper<ImportExcelRequest> rowMapper() {
        return new NhanVienRowMapper();
    }

    @StepScope
    @Bean
    @Qualifier("nhanVienProcessor")
    ItemProcessor<ImportExcelRequest, TransferRequestExcel> nhanVienProcessor() {
        return new NhanVienProcessor();
    }

    @StepScope
    @Bean
    ItemWriter<TransferRequestExcel> excelNhanVienWriter() {
        return new NhanVienWriter();
    }

    @Bean
    Step excelFileToDatabaseStep(@Qualifier("excelNhanVienReader") ItemReader<ImportExcelRequest> excelRequestItemReader
            , JobRepository jobRepository) {
        return new StepBuilder("Excel-File-To-Database-Step", jobRepository)
                .<ImportExcelRequest, TransferRequestExcel>chunk(100, transactionManager)
                .reader(excelRequestItemReader)
                .processor(item -> nhanVienProcessor().process(item))
                .writer(chunk -> excelNhanVienWriter().write(chunk))
                .build();
    }

    @Bean
    Job excelFileToDatabaseJob(JobRepository jobRepository
            , @Qualifier("excelFileToDatabaseStep") Step excelFileToDatabaseStep
            , @Qualifier("jobOperator") org.springframework.batch.core.launch.JobOperator jobOperator
            , JobExplorer jobExplorer) {
        return new JobBuilder("Excel-File-To-Database-Job", jobRepository)
                .start(excelFileToDatabaseStep)
                .build();
    }


}
