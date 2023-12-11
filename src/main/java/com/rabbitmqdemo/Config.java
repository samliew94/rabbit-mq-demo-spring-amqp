package com.rabbitmqdemo;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class Config{

	@Bean
	TopicExchange topic(){
		return new TopicExchange("demo.topic"); // must contain 'fanout'|'direct'|'topic'
	}

	@Profile("receiver")
	private static class ReceiverConfig{

		@Bean
		public Receiver receiver(){
			return new Receiver();
		}

		@Bean
        public Queue autoDeleteQueue1() {
			return new AnonymousQueue();
        }
		
        @Bean
        public Queue autoDeleteQueue2() {
			return new AnonymousQueue();
		}
		
		@Profile("first") // meaning queue1 will only be binded servers with profile=first
        @Bean
        public Binding binding1(TopicExchange exchange, Queue autoDeleteQueue1) {
			return BindingBuilder.bind(autoDeleteQueue1).to(exchange).with("*.orange.*");
        }

		@Profile("second")
        @Bean
        public Binding binding2(TopicExchange exchange, Queue autoDeleteQueue2) {
			return BindingBuilder.bind(autoDeleteQueue2).to(exchange).with("*.*.rabbit");
        }
		
		@Profile("second")
        @Bean
        public Binding binding3(TopicExchange exchange, Queue autoDeleteQueue2) {
			return BindingBuilder.bind(autoDeleteQueue2).to(exchange).with("lazy.#");
        }

	}

	@Profile("sender")
	@Bean
	public Sender sender(){
		return new Sender();
	}

}