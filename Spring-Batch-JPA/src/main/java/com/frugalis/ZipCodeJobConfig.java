package com.frugalis;

import com.frugalis.batchconfig.ZipCOdeMapper;
import com.frugalis.entity.ZIPCodes;
import com.frugalis.repository.ZipCOdeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class ZipCodeJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;



    @Autowired
    ZipCOdeRepository zipCOdeRepository;




    @Bean
    public FlatFileItemReader<ZIPCodes> personItemReader() {
        FlatFileItemReader<ZIPCodes> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("data.csv"));

        DefaultLineMapper<ZIPCodes> customerLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(";");

        tokenizer.setNames(new String[] {"Zip_Code","Official_USPS_city_name","Official_USPS_State_Code","Official_State_Name","ZCTA","ZCTA_parent","Population","Density","Primary_Official_County_Code","Primary_Official_County_Name","Official_County_Name","Official_County_Code","Imprecise","Military","Timezone","Geo_Point"});

        customerLineMapper.setLineTokenizer(tokenizer);

        customerLineMapper.setFieldSetMapper(new ZipCOdeMapper());
        customerLineMapper.afterPropertiesSet();
        reader.setLineMapper(customerLineMapper);

        return reader;
    }

    @Bean
    public ItemWriter<ZIPCodes> zipCodeWriter(){
        // return new InvoiceItemWriter(); // Using lambda expression code instead of a separate implementation
        return zipcodes -> {
            System.out.println("Saving zipcodes : " +zipcodes);
            zipCOdeRepository.saveAll(zipcodes);
           // repository.saveAll(invoices);
        };
    }

  /*  @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public JdbcBatchItemWriter<ZIPCodes> personItemWriter() {
        JdbcBatchItemWriter<ZIPCodes> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("INSERT INTO PERSON VALUES (:id, :firstName, :lastName, :birthdate)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }*/

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<ZIPCodes, ZIPCodes>chunk(1000)
                .reader(personItemReader())

                .writer(zipCodeWriter())
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }
}
