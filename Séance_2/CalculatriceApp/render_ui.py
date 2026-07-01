"""Génère des maquettes fidèles au code Compose pour les deux calculatrices."""
from PIL import Image, ImageDraw, ImageFont

# ------- Couleurs (identiques au code CalcButton.kt / *Screen.kt) -------
BG        = (28, 28, 30)        # 0xFF1C1C1E
DIGIT     = (46, 46, 51)        # 0xFF2E2E33
OPERATOR  = (255, 149, 0)       # 0xFFFF9500
FUNCTION  = (74, 74, 80)        # 0xFF4A4A50
EQUALS    = (0, 184, 148)       # 0xFF00B894
WHITE     = (255, 255, 255)
FRAME     = (10, 10, 12)
SCREEN_TXT= (255, 255, 255)


def load_font(size, bold=False):
    candidates = [
        "/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf" if bold
        else "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf",
        "/usr/share/fonts/truetype/liberation/LiberationSans-Regular.ttf",
    ]
    for c in candidates:
        try:
            return ImageFont.truetype(c, size)
        except Exception:
            continue
    return ImageFont.load_default()


def kind_color(kind):
    return {"digit": DIGIT, "op": OPERATOR, "fn": FUNCTION, "eq": EQUALS}[kind]


def draw_circle_button(draw, cx, cy, r, label, kind, font):
    color = kind_color(kind)
    draw.ellipse([cx - r, cy - r, cx + r, cy + r], fill=color)
    bbox = draw.textbbox((0, 0), label, font=font)
    tw, th = bbox[2] - bbox[0], bbox[3] - bbox[1]
    draw.text((cx - tw / 2 - bbox[0], cy - th / 2 - bbox[1]), label, fill=WHITE, font=font)


def draw_phone_frame(size, portrait=True):
    W, H = size
    img = Image.new("RGB", (W, H), (245, 245, 247))
    d = ImageDraw.Draw(img)
    # Cadre du téléphone
    margin = 30
    radius = 70
    d.rounded_rectangle([margin, margin, W - margin, H - margin],
                        radius=radius, fill=FRAME)
    return img, d, margin, radius


# ============================ PORTRAIT : SIMPLE ============================
def render_portrait(path):
    W, H = 620, 1300
    img, d, m, rad = draw_phone_frame((W, H), portrait=True)

    inner = [m + 16, m + 16, W - m - 16, H - m - 16]
    ix0, iy0, ix1, iy1 = inner
    d.rounded_rectangle(inner, radius=50, fill=BG)

    # Encoche
    d.rounded_rectangle([W/2 - 60, m + 24, W/2 + 60, m + 44], radius=10, fill=(0, 0, 0))

    # ---- Zone d'affichage ----
    disp_font = load_font(96, bold=False)
    small_font = load_font(28)
    d.text((ix0 + 24, iy0 + 70), "Calculatrice — Simple", fill=(150, 150, 155), font=small_font)
    txt = "3 402"
    bbox = d.textbbox((0, 0), txt, font=disp_font)
    d.text((ix1 - 24 - (bbox[2]-bbox[0]), iy0 + 260), txt, fill=SCREEN_TXT, font=disp_font)

    # ---- Grille de boutons 4 colonnes x 5 lignes ----
    rows = [
        [("C", "fn"), ("⌫", "fn"), ("%", "fn"), ("÷", "op")],
        [("7", "digit"), ("8", "digit"), ("9", "digit"), ("×", "op")],
        [("4", "digit"), ("5", "digit"), ("6", "digit"), ("−", "op")],
        [("1", "digit"), ("2", "digit"), ("3", "digit"), ("+", "op")],
        [("±", "digit"), ("0", "digit"), (".", "digit"), ("=", "eq")],
    ]
    cols = 4
    grid_top = iy0 + 430
    grid_bottom = iy1 - 30
    pad_x = ix0 + 30
    grid_w = (ix1 - 30) - pad_x
    cell_w = grid_w / cols
    cell_h = (grid_bottom - grid_top) / len(rows)
    r = min(cell_w, cell_h) / 2 - 12
    btn_font = load_font(46)

    for ri, row in enumerate(rows):
        for ci, (label, kind) in enumerate(row):
            cx = pad_x + cell_w * ci + cell_w / 2
            cy = grid_top + cell_h * ri + cell_h / 2
            draw_circle_button(d, cx, cy, r, label, kind, btn_font)

    img.save(path)
    print("Enregistré:", path)


# ======================== PAYSAGE : SCIENTIFIQUE ========================
def render_landscape(path):
    W, H = 1500, 720
    img, d, m, rad = draw_phone_frame((W, H), portrait=False)

    inner = [m + 16, m + 16, W - m - 16, H - m - 16]
    ix0, iy0, ix1, iy1 = inner
    d.rounded_rectangle(inner, radius=50, fill=BG)

    small_font = load_font(24)
    d.text((ix0 + 24, iy0 + 20), "Calculatrice — Scientifique", fill=(150, 150, 155), font=small_font)

    # ---- Affichage ----
    disp_font = load_font(72)
    txt = "1.4142135"
    bbox = d.textbbox((0, 0), txt, font=disp_font)
    d.text((ix1 - 30 - (bbox[2]-bbox[0]), iy0 + 55), txt, fill=SCREEN_TXT, font=disp_font)

    # ---- Grille 6 colonnes x 6 lignes ----
    rows = [
        [("sin","fn"),("cos","fn"),("tan","fn"),("C","fn"),("⌫","fn"),("÷","op")],
        [("ln","fn"),("log","fn"),("√","fn"),("7","digit"),("8","digit"),("9","digit")],
        [("x²","fn"),("xʸ","fn"),("1/x","fn"),("4","digit"),("5","digit"),("×","op")],
        [("π","fn"),("e","fn"),("%","fn"),("1","digit"),("2","digit"),("3","digit")],
        [("±","digit"),("6","digit"),("0","digit"),(".","digit"),("−","op"),("+","op")],
        [("=","eq"),("","none"),("","none"),("","none"),("","none"),("","none")],
    ]
    cols = 6
    grid_top = iy0 + 165
    grid_bottom = iy1 - 20
    pad_x = ix0 + 24
    grid_w = (ix1 - 24) - pad_x
    cell_w = grid_w / cols
    cell_h = (grid_bottom - grid_top) / len(rows)
    r = min(cell_w, cell_h) / 2 - 8
    btn_font = load_font(34)

    for ri, row in enumerate(rows):
        for ci, (label, kind) in enumerate(row):
            if kind == "none":
                continue
            cx = pad_x + cell_w * ci + cell_w / 2
            cy = grid_top + cell_h * ri + cell_h / 2
            draw_circle_button(d, cx, cy, r, label, kind, btn_font)

    img.save(path)
    print("Enregistré:", path)


if __name__ == "__main__":
    render_portrait("images/portrait_simple.png")
    render_landscape("images/landscape_scientific.png")
