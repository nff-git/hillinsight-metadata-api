package hillinsight.metadata.api.utils.email;


import hillinsight.metadata.api.dto.email.SendEmailRequest;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 发送邮件util
 * @author  wcy
 * @date 2020年9月21日10:24:27
 */
public class SendEmailutil {

    //判断发送邮件是否成功
    private static  boolean isSuccess = true;

    /**
     * 发送邮件
     */
    public static boolean sendEmail(SendEmailRequest sendEmailRequest){

        /**
             * 腾讯企业邮箱支持通过客户端进行邮件管理。
             * POP3/SMTP协议
             * 收发邮件服务器地址分别如下。
             * 接收邮件服务器：pop.exmail.qq.com (端口 110)
             * 发送邮件服务器：smtp.exmail.qq.com (端口 25)
             * 同时支持SSL加密方式登录，此时需要更改一下端口号。
             * 接收服务器端口：995
             * 发送服务器端口：465
         */
        Properties properties = new Properties();
        properties.setProperty("mail.host",sendEmailRequest.getEmailTypeRequest());
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth","false");

        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendEmailRequest.getEmailAddressRequest(),sendEmailRequest.getAuthorizationCodeRequest());
            }
        });
        //开启debug模式
        session.setDebug(true);
        //获取连接对象
        Transport transport = null;
        //连接服务器
        try {
            transport =  session.getTransport();
            transport.connect(sendEmailRequest.getEmailTypeRequest(),
                    sendEmailRequest.getEmailAddressRequest(),sendEmailRequest.getAuthorizationCodeRequest());
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress(sendEmailRequest.getEmailAddressRequest()));
        //邮件接收人(支持发送多人)
        List<InternetAddress> internetAddressList =new  ArrayList<InternetAddress>();
        String[] addressList = sendEmailRequest.getReceiverAddressList().split(",");
        for (int i = 0; i < addressList.length; i++) {
                internetAddressList.add(new InternetAddress(addressList[i]));
            }
        InternetAddress[] internetAddresses = internetAddressList.toArray(
                new InternetAddress[internetAddressList.size()]);
        mimeMessage.setRecipients(Message.RecipientType.TO,internetAddresses);
        //邮件标题
        mimeMessage.setSubject(sendEmailRequest.getSubject());
        //邮件内容
        mimeMessage.setContent(sendEmailRequest.getContent(),"text/html;charset=UTF-8");
        //发送邮件
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        //关闭连接
        transport.close();
        } catch (MessagingException e) {
            isSuccess = false;
            e.printStackTrace();
        }finally {
            return  isSuccess;
        }
    }
}
