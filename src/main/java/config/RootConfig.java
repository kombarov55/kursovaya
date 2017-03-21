package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by nikolaykombarov on 21.03.17.
 */

@Configuration
@ComponentScan(basePackageClasses = {db.ItemRepository.class, model.Item.class})
@ComponentScan("org.zkos")

public class RootConfig {



}
