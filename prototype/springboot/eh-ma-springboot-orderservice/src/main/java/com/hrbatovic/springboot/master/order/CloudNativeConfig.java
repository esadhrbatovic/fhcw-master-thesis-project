package com.hrbatovic.springboot.master.order;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@RegisterReflectionForBinding({
        io.jsonwebtoken.Claims.class,
        io.jsonwebtoken.Jwts.class,
        io.jsonwebtoken.Jwts.SIG.class,
        io.jsonwebtoken.impl.security.StandardSecureDigestAlgorithms.class,
        io.jsonwebtoken.impl.security.StandardKeyOperations.class,
        io.jsonwebtoken.impl.security.StandardEncryptionAlgorithms.class,
        io.jsonwebtoken.impl.security.StandardKeyAlgorithms.class,
        io.jsonwebtoken.impl.io.StandardCompressionAlgorithms.class,
        io.jsonwebtoken.impl.security.KeysBridge.class,
        io.jsonwebtoken.impl.DefaultClaimsBuilder.class,
        io.jsonwebtoken.impl.DefaultJwtParser.class,
        io.jsonwebtoken.impl.DefaultJwtParserBuilder.class,
        io.jsonwebtoken.impl.DefaultJwtBuilder.class,
        })
public class CloudNativeConfig {}