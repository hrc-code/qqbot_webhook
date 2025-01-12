package com.hrc.bot.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrc.bot.config.BotConfig;
import com.hrc.bot.entity.Payload;
import com.hrc.bot.entity.hut.grade.resp.DataDTO;
import com.hrc.bot.entity.hut.grade.resp.GradeResp;
import com.hrc.bot.entity.sendrequest.SendRequest;
import com.hrc.bot.entity.validate.CustomPrivateKey;
import com.hrc.bot.entity.validate.CustomPublicKey;
import com.hrc.bot.entity.validate.ValidationRequest;
import com.hrc.bot.entity.validate.ValidationResponse;
import com.hrc.bot.openapi.HutOpenApi;
import com.hrc.bot.openapi.QQBotOpenApi;
import jakarta.annotation.Resource;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.util.HexFormat;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/webhook")
public class BotController {
    @Resource
    private HutOpenApi hutOpenApi;
    @Resource
    private QQBotOpenApi qQBotOpenApi;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(BotController.class);

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @PostMapping("/")
    public ResponseEntity<?> handleValidation(@RequestBody String rawBody,
                                              @RequestHeader("X-Signature-Ed25519") String sig,
                                              @RequestHeader("X-Signature-Timestamp") String timestamp) {
        try {
            String seed = prepareSeed(BotConfig.APP_SECRET);
            KeyPair keyPair = generateEd25519KeyPair(seed.getBytes(StandardCharsets.UTF_8));
            Payload<?> payload = JSON.parseObject(rawBody, Payload.class);
            switch (payload.getOp()) {
                case 0 -> {
                    boolean isValid = verifySignature(sig, timestamp, rawBody.getBytes(StandardCharsets.UTF_8), keyPair.getPublic().getEncoded());
                    if (isValid) {
                        String eventType = payload.getT();
                        if ("C2C_MESSAGE_CREATE".equals(eventType)) {
                            String msg  = " 你好";

                            Payload<SendRequest> sendRequestPayload = JSON.parseObject(rawBody, new TypeReference<>() {
                            });
                            SendRequest d = sendRequestPayload.getD();
                            String content = d.getContent().trim();
                            if ("查询电费".equals(content)) {

                            } else if ("查询大一成绩".equals(content) || "/grade1".equals(content)) {
                                String grade = hutOpenApi.getGrade("2022-2023-1");
                                StringJoiner stringJoiner = resolveGrade(grade);
                                String s1 = stringJoiner.toString();
                                String grade1 = hutOpenApi.getGrade("2022-2023-2");
                                StringJoiner stringJoiner1 = resolveGrade(grade1);
                                String s2 = stringJoiner1.toString();

                                msg = "大一上 \n"+ s1 +'\n'+"大一下" +'\n'+ s2;

                            }else if("查询大二成绩".equals(content) || "/grade2".equals(content)) {
                                String grade = hutOpenApi.getGrade("2023-2024-1");
                                StringJoiner stringJoiner = resolveGrade(grade);
                                String s1 = stringJoiner.toString();
                                String grade1 = hutOpenApi.getGrade("2023-2024-2");
                                StringJoiner stringJoiner1 = resolveGrade(grade1);
                                String s2 = stringJoiner1.toString();

                                msg = "大二上 \n"+ s1 +'\n'+"大二下" +'\n'+ s2;


                            } else if ("查询本学期成绩".equals(content)) {
                                String nowGrade = hutOpenApi.getNowGrade();
                                StringJoiner sj = resolveGrade(nowGrade);
                                msg = sj.toString();

                            }
                            else if ("查询大三成绩".equals(content) || "/grade3".equals(content)) {
                                String grade = hutOpenApi.getGrade("2024-2025-1");
                                StringJoiner stringJoiner = resolveGrade(grade);
                                String s1 = stringJoiner.toString();
                                String grade1 = hutOpenApi.getGrade("2024-2025-2");
                                StringJoiner stringJoiner1 = resolveGrade(grade1);
                                String s2 = stringJoiner1.toString();

                                msg = "大三上 \n"+ s1 +'\n'+"大三下" +'\n'+ s2;

                            } else if("查询大四成绩".equals(content) || "/grade4".equals(content)) {
                                String grade = hutOpenApi.getGrade("2025-2026-1");
                                StringJoiner stringJoiner = resolveGrade(grade);
                                String s1 = stringJoiner.toString();
                                String grade1 = hutOpenApi.getGrade("2025-2026-2");
                                StringJoiner stringJoiner1 = resolveGrade(grade1);
                                String s2 = stringJoiner1.toString();

                                msg = "大四上 \n"+ s1 +'\n'+"大四下" +'\n'+ s2;

                            }

                            msg = JSON.toJSONString(msg);
                            logger.info(msg);
                            qQBotOpenApi.doSendMsg(sendRequestPayload,msg);
                        }
                    }
                    //处理机器人逻辑
                    return ResponseEntity.ok(isValid);
                }
                case 13 -> {
                    //验证签名
                    logger.info("验证有效性...");
                    ValidationRequest validationPayload = objectMapper.convertValue(payload.getD(), ValidationRequest.class);
                    byte[] message = (validationPayload.getEvent_ts() + validationPayload.getPlain_token()).getBytes(StandardCharsets.UTF_8);
                    byte[] signature = signMessage(keyPair.getPrivate(), message);
                    ValidationResponse resp = new ValidationResponse(validationPayload.getPlain_token(), HexFormat.of().formatHex(signature));
                    return ResponseEntity.ok(resp);
                }
                default -> logger.info("未处理操作：{}", JSON.toJSONString(payload));
            }
        } catch (Exception e) {
            logger.error("验证失败：", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }

    private static StringJoiner resolveGrade(String nowGrade) {
        GradeResp gradeResp = JSON.parseObject(nowGrade, GradeResp.class);
        DataDTO data = gradeResp.getData().get(0);
        Set<String> gradeSet = data.getAchievement().stream().map(achievementDTO -> {
            //课程名
            String courseName = achievementDTO.getCourseName();
            //成绩
            String fraction = achievementDTO.getFraction();
            return courseName + ":" + fraction;
        }).collect(Collectors.toSet());
        StringJoiner sj = new StringJoiner("\n");
        for (String grade : gradeSet) {
            sj.add(grade);
        }
        return sj;
    }

    public static boolean verifySignature(String signatureHex, String timestamp, byte[] httpBody, byte[] publicKeyBytes) {
        try {
            // 解码签名
            byte[] sig = HexFormat.of().parseHex(signatureHex);
            // 检查签名长度和格式
            if (sig.length != 64 || (sig[63] & 0xE0) != 0) {
                logger.warn("Invalid signature format");
                return false;
            }
            // 组成签名体
            ByteArrayOutputStream msg = new ByteArrayOutputStream();
            msg.write(timestamp.getBytes(StandardCharsets.UTF_8));
            msg.write(httpBody);
            Ed25519Signer verifier = new Ed25519Signer();
            verifier.init(false, new Ed25519PublicKeyParameters(publicKeyBytes, 0));
            verifier.update(msg.toByteArray(), 0, msg.size());
            return verifier.verifySignature(sig);
        } catch (Exception e) {
            logger.error("验证签名报错：", e);
            return false;
        }
    }

    private static String prepareSeed(String seed) {
        if (seed.length() < 32) {
            seed = seed.repeat(2);
        }
        return seed.substring(0, 32);
    }

    private static KeyPair generateEd25519KeyPair(byte[] seed) {
        Ed25519KeyPairGenerator generator = new Ed25519KeyPairGenerator();
        generator.init(new KeyGenerationParameters(null, 32));
        // 使用种子初始化私钥参数
        Ed25519PrivateKeyParameters privateKeyParams = new Ed25519PrivateKeyParameters(seed, 0);
        // 从私钥参数中提取公钥参数
        Ed25519PublicKeyParameters publicKeyParams = privateKeyParams.generatePublicKey();
        // 将参数转换为字节数组
        byte[] privateKeyBytes = privateKeyParams.getEncoded();
        byte[] publicKeyBytes = publicKeyParams.getEncoded();
        return new KeyPair(
                new CustomPublicKey(publicKeyBytes),
                new CustomPrivateKey(privateKeyBytes)
        );
    }

    private static byte[] signMessage(PrivateKey privateKey, byte[] message) {
        Ed25519Signer signer = new Ed25519Signer();
        signer.init(true, new Ed25519PrivateKeyParameters(privateKey.getEncoded(), 0));
        signer.update(message, 0, message.length);
        return signer.generateSignature();
    }
}
