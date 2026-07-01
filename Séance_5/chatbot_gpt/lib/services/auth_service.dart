import 'package:shared_preferences/shared_preferences.dart';

/// Authentification simple stockée localement (pour la démonstration).
/// Dans une vraie application, on utiliserait Firebase Auth ou un back-end.
class AuthService {
  static const _keyLoggedIn = 'logged_in';
  static const _keyUsername = 'username';

  Future<bool> isLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getBool(_keyLoggedIn) ?? false;
  }

  Future<String> getUsername() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_keyUsername) ?? 'Utilisateur';
  }

  /// Connexion : accepte tout couple email / mot de passe non vide
  /// (mot de passe >= 6 caractères).
  Future<bool> login(String email, String password) async {
    if (email.trim().isEmpty || password.length < 6) return false;
    final prefs = await SharedPreferences.getInstance();
    await prefs.setBool(_keyLoggedIn, true);
    await prefs.setString(_keyUsername, email.split('@').first);
    return true;
  }

  Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setBool(_keyLoggedIn, false);
  }
}
