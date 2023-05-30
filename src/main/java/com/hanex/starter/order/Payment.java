package com.hanex.starter.order;

import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.UUID;

public class Payment {

	@Id
	private UUID id;

	private String orderCode;

	private Long amount;

	private Instant paymentDateTime;
}
