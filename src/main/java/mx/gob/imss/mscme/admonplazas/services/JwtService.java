package mx.gob.imss.mscme.admonplazas.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateToken(
            Usuario usuario) {

        Map<String, Object> extraClaims = new HashMap<>();

        // Agrega el ID del usuario como claims personalizados
        extraClaims.put("idUsuario", usuario.getIdUsuario());
        extraClaims.put("idPerfil", usuario.getPerfil() != null ? usuario.getPerfil().getIdPerfil() : null);
        extraClaims.put("perfil", usuario.getPerfil() != null ? usuario.getPerfil().getDesPerfil() : null);
        extraClaims.put("idSubperfil", usuario.getSubperfil() != null ? usuario.getSubperfil().getIdSubperfil() : null);
        extraClaims.put("subperfil", usuario.getSubperfil() != null ? usuario.getSubperfil().getDesSubperfil() : null);
        extraClaims.put("nomNombre", usuario.getNomNombre());
        extraClaims.put("nomApellidoPaterno", usuario.getNomApellidoPaterno());
        extraClaims.put("nomApellidoMaterno", usuario.getNomApellidoMaterno());
        extraClaims.put("cveMatricula", usuario.getCveMatricula() != null ? usuario.getCveMatricula() : null);
        extraClaims.put("refCurp", usuario.getRefCurp() != null ? usuario.getRefCurp() : null);
        extraClaims.put("refPasaporte", usuario.getRefPasaporte() != null ? usuario.getRefPasaporte() : null);
        extraClaims.put("refEmail", usuario.getRefEmail());

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generarTokenRecuperacion(
            Usuario usuario) {

        // 4 HORAS
        long jwtExpiration = 14400000L;

        Map<String, Object> extraClaims = new HashMap<>();

        // Agrega el ID del usuario como claims personalizados

        extraClaims.put("nomNombre", usuario.getNomNombre());
        extraClaims.put("nomApellidoPaterno", usuario.getNomApellidoPaterno());
        extraClaims.put("nomApellidoMaterno", usuario.getNomApellidoMaterno());
        extraClaims.put("refEmail", usuario.getRefEmail());
        extraClaims.put("refCurp", usuario.getRefCurp() != null ? usuario.getRefCurp() : null);

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}