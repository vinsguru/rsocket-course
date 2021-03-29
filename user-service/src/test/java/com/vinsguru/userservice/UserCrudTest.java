package com.vinsguru.userservice;

import com.vinsguru.userservice.dto.OperationType;
import com.vinsguru.userservice.dto.UserDto;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserCrudTest {

	private RSocketRequester requester;

	@Autowired
	private RSocketRequester.Builder builder;

	@BeforeAll
	public void setRequester(){
		this.requester = this.builder
				.transport(TcpClientTransport.create("localhost", 7071));
	}

	@Test
	void getAllUsersTest() {
		Flux<UserDto> flux = this.requester.route("user.get.all")
				.retrieveFlux(UserDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(flux)
				.expectNextCount(3)
				.verifyComplete();
	}

	@Test
	void getSingleUserTest() {

		UserDto userDto = this.getRandomUser();

		Mono<UserDto> mono = this.requester.route("user.get.{id}", userDto.getId())
				.retrieveMono(UserDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(mono)
				.expectNextMatches(d -> d.getId().equals(userDto.getId()))
				.verifyComplete();
	}

	@Test
	void postUserTest() {

		UserDto userDto = new UserDto();
		userDto.setName("vins");
		userDto.setBalance(10);

		Mono<UserDto> mono = this.requester.route("user.create")
				.data(userDto)
				.retrieveMono(UserDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(mono)
				.expectNextCount(1)
				.verifyComplete();
	}

	@Test
	void putUserTest() {

		UserDto userDto = this.getRandomUser();
		userDto.setBalance(-10);

		Mono<UserDto> mono = this.requester.route("user.update.{id}", userDto.getId())
				.data(userDto)
				.retrieveMono(UserDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(mono)
				.expectNextMatches(d -> d.getBalance() == -10)
				.verifyComplete();
	}

	@Test
	void deleteUserTest() throws InterruptedException {
		UserDto userDto = this.getRandomUser();
		Mono<Void> mono = this.requester.route("user.delete.{id}", userDto.getId())
				.send();

		StepVerifier.create(mono)
				.verifyComplete();

		Thread.sleep(1000);

		Flux<UserDto> flux = this.requester.route("user.get.all")
				.retrieveFlux(UserDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(flux)
				.expectNextCount(2)
				.verifyComplete();


	}

	@Test
	void metadataTest()  {

		MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.APPLICATION_CBOR.getString());

		UserDto dto = new UserDto();
		dto.setName("md");
		dto.setBalance(100);
		Mono<Void> mono = this.requester.route("user.operation.type")
				.metadata(OperationType.PUT, mimeType)
				.data(dto)
				.send();

		StepVerifier.create(mono)
				.verifyComplete();


	}


	private UserDto getRandomUser(){
		return this.requester.route("user.get.all")
				.retrieveFlux(UserDto.class)
				.next()
				.block();
	}

}
