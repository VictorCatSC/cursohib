package com.victor.cursohibernate.services;

import com.victor.cursohibernate.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService
{
	void sendOrderConfirmation(Pedido pedido);

	void sendEmail(SimpleMailMessage msg);
}
