package com.roadmountain.sim.register

import com.roadmountain.sim.domain.entity.Registration
import com.roadmountain.sim.gmail.GmailService
import com.roadmountain.sim.repository.RegistrationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegistrationService(
    private val registrationRepository: RegistrationRepository,
    private val gmailService: GmailService
) {
    fun register(registration: Registration) {
        requireNotNull(registration.privacy) { "Registration privacy should not be null during register moment." }

        registrationRepository.save(registration)
        val content = gmailService.createEmail(
            to = registration.privacy.email,
            subject = "【SIMカード開通】登録完了のご案内",
            bodyText = """
                当店をご利用いただきありがとうございます。

                以下のSIMカードの登録が完了しました。
                カードの開通日は、登録日の3日後となります。(オーストラリアの土日祝日を除く)
                
                SIMカードナンバー: ${registration.simNo}
                
                上記SIMカードナンバーは、サービスのご利用やメールにてお問い合わせをいただく際等に必要となりますので、お手元にお控えください。
                
                ■お問合せ（必ずSIMカードナンバーをご記入下さい）
                
                メール  ‪roadmountain.shop@gmail.com‬
                営業時間 10:00～17:00（土日祝休み）
                
                ※このメールをお送りしているアドレスは、送信専用となっており、返信いただいてもご回答いたしかねます。
            """.trimIndent()
        )
        gmailService.sendMessage(content)
    }

    fun getCountries(): Map<String, String> {
        return Locale.getISOCountries()
            .map {
                val locale = Locale("en", it)
                locale.country to locale.displayCountry
            }
            .toList()
            .sortedBy { it.second }
            .toMap()
    }
}