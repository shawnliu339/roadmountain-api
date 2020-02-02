package com.roadmountain.sim.gmail

import com.google.api.client.util.Base64
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.model.Message
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*
import javax.activation.DataHandler
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.mail.Multipart
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


@Service
class GMailService(
) {
    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    fun createEmail(
        to: String,
        from: String?,
        subject: String,
        bodyText: String
    ): MimeMessage {
        val props = Properties()
        val session: Session = Session.getDefaultInstance(props, null)
        val email = MimeMessage(session)
        email.setFrom(InternetAddress(from))
        email.addRecipient(
            javax.mail.Message.RecipientType.TO,
            InternetAddress(to)
        )
        email.subject = subject
        email.setText(bodyText)
        return email
    }

    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    fun createMessageWithEmail(emailContent: MimeMessage): Message {
        val buffer = ByteArrayOutputStream()
        emailContent.writeTo(buffer)
        val bytes: ByteArray = buffer.toByteArray()
        val encodedEmail: String = Base64.encodeBase64URLSafeString(bytes)
        return Message().apply { raw = encodedEmail }
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to Email address of the receiver.
     * @param from Email address of the sender, the mailbox account.
     * @param subject Subject of the email.
     * @param bodyText Body text of the email.
     * @param file Path to the file to be attached.
     * @return MimeMessage to be used to send email.
     * @throws MessagingException
     */
    fun createEmailWithAttachment(
        to: String?,
        from: String?,
        subject: String?,
        bodyText: String?,
        file: File
    ): MimeMessage? {
        val props = Properties()
        val session = Session.getDefaultInstance(props, null)
        val email = MimeMessage(session)
        email.setFrom(InternetAddress(from))
        email.addRecipient(
            javax.mail.Message.RecipientType.TO,
            InternetAddress(to)
        )
        email.subject = subject
        var mimeBodyPart = MimeBodyPart()
        mimeBodyPart.setContent(bodyText, "text/plain")
        val multipart: Multipart = MimeMultipart()
        multipart.addBodyPart(mimeBodyPart)
        mimeBodyPart = MimeBodyPart()
        val source: DataSource = FileDataSource(file)
        mimeBodyPart.setDataHandler(DataHandler(source))
        mimeBodyPart.setFileName(file.getName())
        multipart.addBodyPart(mimeBodyPart)
        email.setContent(multipart)
        return email
    }

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param emailContent Email to be sent.
     * @return The sent message
     * @throws MessagingException
     * @throws IOException
     */
    fun sendMessage(
        service: Gmail,
        userId: String,
        emailContent: MimeMessage
    ): Message {
        var message = createMessageWithEmail(emailContent)
        message = service.users().messages().send(userId, message).execute()
        println("Message id: " + message.id)
        println(message.toPrettyString())
        return message
    }
}