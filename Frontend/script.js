"use strict";

// ===== scripts/config.js =====
const USE_MOCK_DATA = false;                    
const API_BASE = "http://localhost:8080/api/products";  // Points directly to your controller mapping
const currency = "Rs.";                        

// ===== scripts/mock-data.js =====
const MOCK_CATEGORIES = [
  {"id": 1, "name": "Vegetables", "image": "🥦", "itemCount": 22},
  {"id": 2, "name": "Fruits", "image": "🍓", "itemCount": 22},
  {"id": 3, "name": "Dairy", "image": "🥛", "itemCount": 22},
  {"id": 4, "name": "Meat", "image": "🥩", "itemCount": 22},
  {"id": 5, "name": "Seafood", "image": "🦐", "itemCount": 22},
  {"id": 6, "name": "Bakery", "image": "🥐", "itemCount": 22},
  {"id": 7, "name": "Pantry and Baking", "image": "🥫", "itemCount": 22},
  {"id": 8, "name": "Cereals", "image": "🥣", "itemCount": 22},
  {"id": 9, "name": "Snacks", "image": "🍿", "itemCount": 22},
  {"id": 10, "name": "Beverages", "image": "🥤", "itemCount": 22},
  {"id": 11, "name": "Frozen Foods", "image": "🧊", "itemCount": 22},
  {"id": 12, "name": "Condiments and Sauces", "image": "🍯", "itemCount": 22},
  {"id": 13, "name": "Household and Cleaning", "image": "🧹", "itemCount": 22},
  {"id": 14, "name": "Health and Personal Care", "image": "🧴", "itemCount": 22},
  {"id": 15, "name": "Pet Care", "image": "🐾", "itemCount": 22}
];

const MOCK_PRODUCTS = [
  {"id": 1, "name": "Farm fresh organic peaches 250g", "price": 7.99, "image": "🍑", "category": "Fresh Items", "discount": 25},
  {"id": 2, "name": "Farm fresh organic avocados 250g", "price": 7.99, "image": "🥑", "category": "Fresh Items", "discount": 25},
  {"id": 3, "name": "Farm fresh organic carrots 250g", "price": 7.99, "image": "🥕", "category": "Fresh Items", "discount": 25},
  {"id": 4, "name": "Farm fresh organic tomatoes 250g", "price": 7.99, "image": "🍅", "category": "Fresh Items", "discount": 25},
  {"id": 5, "name": "Farm fresh organic potatoes 250g", "price": 7.99, "image": "🥔", "category": "Fresh Items", "discount": 25},
  {"id": 6, "name": "Farm fresh organic strawberries 250g", "price": 7.99, "image": "🍓", "category": "Fresh Items", "discount": 25},
  {"id": 7, "name": "Farm fresh organic broccoli 250g", "price": 7.99, "image": "🥦", "category": "Fresh Items", "discount": 25},
  {"id": 8, "name": "Farm fresh organic oranges 250g", "price": 7.99, "image": "🍊", "category": "Fresh Items", "discount": 25},
  {"id": 9, "name": "Farm fresh organic bananas 250g", "price": 7.99, "image": "🍌", "category": "Fresh Items", "discount": 25},
  {"id": 10, "name": "Farm fresh organic peppers 250g", "price": 7.99, "image": "🫑", "category": "Fresh Items", "discount": 25},
  {"id": 11, "name": "Farm fresh organic apples 250g", "price": 6.49, "image": "🍎", "category": "Fresh Items", "discount": 25},
  {"id": 12, "name": "Farm fresh organic grapes 250g", "price": 8.99, "image": "🍇", "category": "Fresh Items", "discount": 25},
  {"id": 13, "name": "Farm fresh organic lemons 250g", "price": 5.99, "image": "🍋", "category": "Fresh Items", "discount": 25},
  {"id": 14, "name": "Farm fresh organic cucumbers 250g", "price": 4.99, "image": "🥒", "category": "Fresh Items", "discount": 25},
  {"id": 15, "name": "Farm fresh organic corn 250g", "price": 5.49, "image": "🌽", "category": "Fresh Items", "discount": 25},
  {"id": 16, "name": "Farm fresh organic pineapples 250g", "price": 9.49, "image": "🍍", "category": "Fresh Items", "discount": 25},
  {"id": 17, "name": "Farm fresh organic watermelon 250g", "price": 6.99, "image": "🍉", "category": "Fresh Items", "discount": 25},
  {"id": 18, "name": "Farm fresh organic mushrooms 250g", "price": 7.49, "image": "🍄", "category": "Fresh Items", "discount": 25},
  {"id": 19, "name": "Farm fresh organic eggplants 250g", "price": 6.29, "image": "🍆", "category": "Fresh Items", "discount": 25},
  {"id": 20, "name": "Farm fresh organic cherries 250g", "price": 11.99, "image": "🍒", "category": "Fresh Items", "discount": 25},
  {"id": 21, "name": "Farm fresh organic mangoes 250g", "price": 8.49, "image": "🥭", "category": "Fresh Items", "discount": 25},
  {"id": 22, "name": "Farm fresh organic kiwis 250g", "price": 7.29, "image": "🥝", "category": "Fresh Items", "discount": 25},
  {"id": 23, "name": "Farm fresh organic garlic 250g", "price": 3.99, "image": "🧄", "category": "Fresh Items", "discount": 25},
  {"id": 24, "name": "Farm fresh organic onions 250g", "price": 3.49, "image": "🧅", "category": "Fresh Items", "discount": 25},
  {"id": 25, "name": "Farm fresh organic coconuts 250g", "price": 6.79, "image": "🥥", "category": "Fresh Items", "discount": 25}
];

// ===== scripts/utils.js =====
const ICON_SOURCES = Object.freeze({
  heart: '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" aria-hidden="true"><path d="M12 21s-7.5-4.6-9.5-9.3C1.2 8.4 3 5 6.4 5c2 0 3.6 1.1 5.6 3.2C14 6.1 15.6 5 17.6 5 21 5 22.8 8.4 21.5 11.7 19.5 16.4 12 21 12 21z"/></svg>',
  cart: '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><circle cx="9" cy="20" r="1.4"/><circle cx="18" cy="20" r="1.4"/><path d="M2 3h3l2.7 12.4a1.5 1.5 0 0 0 1.5 1.2h8.6a1.5 1.5 0 0 0 1.5-1.1L21 8H6"/></svg>',
  arrow: '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M5 12h14M13 6l6 6-6 6"/></svg>'
});

function icon(name) {
  const doc = new DOMParser().parseFromString(ICON_SOURCES[name], "image/svg+xml");
  return document.importNode(doc.documentElement, true);
}

function el(tag, { className, text, attrs, children } = {}) {
  const node = document.createElement(tag);
  if (className) node.className = className;
  if (text !== undefined) node.textContent = text;
  if (attrs) {
    for (const [name, value] of Object.entries(attrs)) {
      if (/^(on|style$)/i.test(name)) throw new Error(`Blocked attribute: ${name}`);
      node.setAttribute(name, String(value));
    }
  }
  if (children) node.append(...children);
  return node;
}

const SAFE_ID = /^[A-Za-z0-9_-]{1,64}$/;                 
const CONTROL_CHARS = /[\u0000-\u001f\u007f-\u009f]/g;     

const cleanText = (value, max = 120) =>
  String(value ?? "").replace(CONTROL_CHARS, "").trim().slice(0, max);

const cleanNumber = (value, min, max, fallback = 0) => {
  const n = Number(value);
  return Number.isFinite(n) ? Math.min(max, Math.max(min, n)) : fallback;
};

const money = n => `${currency} ${cleanNumber(n, 0, 1e9).toFixed(2)}`;

const IMAGE_EXT = /\.(png|jpe?g|webp|svg|gif)$/i;
function safeImageURL(value) {
  const raw = String(value ?? "").trim();
  if (!raw || raw.length > 500 || !IMAGE_EXT.test(raw.split(/[?#]/)[0])) return null;
  const normalized = raw.startsWith("http") || raw.startsWith("/") ? raw : "/" + raw;
  try {
    const url = new URL(normalized, window.location.origin);
    return (url.protocol === "https:" || url.protocol === "http:") ? url.href : null;
  } catch {
    return null;
  }
}

function picture(value) {
  const src = safeImageURL(value);
  if (src) {
    return el("img", { attrs: { src, alt: "", loading: "lazy", decoding: "async", referrerpolicy: "no-referrer" } });
  }
  return document.createTextNode(cleanText(value, 16));
}

// ===== scripts/api.js =====
const REQUEST_TIMEOUT_MS = 8000;

async function request(path, options = {}) {
  const controller = new AbortController();
  const timer = setTimeout(() => controller.abort(), REQUEST_TIMEOUT_MS);
  try {
    const res = await fetch(API_BASE + path, {
      method: "GET",
      credentials: "same-origin",
      ...options,
      headers: {
        Accept: "application/json",
        ...(options.body ? { "Content-Type": "application/json" } : {}),
        ...(options.headers || {})
      },
      signal: controller.signal
    });
    if (!res.ok) throw new Error(`Request failed (${res.status})`);
    if (!(res.headers.get("content-type") || "").includes("application/json")) {
      throw new Error("Unexpected response type");
    }
    return await res.json();
  } finally {
    clearTimeout(timer);
  }
}

function sanitizeCategory(c) {
  if (!c || typeof c !== "object" || !SAFE_ID.test(String(c.id))) return null;
  const name = cleanText(c.name, 60);
  if (!name) return null;
  return {
    id: String(c.id),
    name,
    image: String(c.image ?? ""),
    itemCount: Math.round(cleanNumber(c.itemCount, 0, 1e6))
  };
}

// Map backend database column fields to frontend expected fields
function sanitizeProduct(p) {
  if (!p || typeof p !== "object" || !SAFE_ID.test(String(p.id))) return null;
  
  const name = cleanText(p.productName || p.name, 120);
  if (!name) return null;

  const rawPrice = p.price !== undefined ? p.price : p.effectivePrice;

  return {
    id: String(p.id),
    name: name,
    price: Math.round(cleanNumber(rawPrice, 0, 1e6) * 100) / 100,
    image: String(p.productImage || p.image || ""),
    category: cleanText(p.catagory || p.productCategory || p.category, 40) || "General",
    discount: Math.round(cleanNumber(p.offerFraction || p.discount, 0, 100))
  };
}

const asList = data => {
  if (!Array.isArray(data)) throw new Error("Expected a list from the server");
  return data;
};

async function getCategories() {
  if (USE_MOCK_DATA) return asList(MOCK_CATEGORIES).map(sanitizeCategory).filter(Boolean);
  try {
    const data = await request("/categories");
    return asList(data).map(sanitizeCategory).filter(Boolean);
  } catch {
    return asList(MOCK_CATEGORIES).map(sanitizeCategory).filter(Boolean);
  }
}

async function getProducts({ search = "", category = "" } = {}) {
  const q = cleanText(search, 100);
  const cat = cleanText(category, 60);

  let data;
  if (USE_MOCK_DATA) {
    const needle = q.toLowerCase();
    data = MOCK_PRODUCTS.filter(p =>
      p.name.toLowerCase().includes(needle) && (!cat || p.category === cat));
  } else {
    if (q) {
      data = await request(`/search?query=${encodeURIComponent(q)}`);
    } else if (cat) {
      data = await request(`/category/${encodeURIComponent(cat)}`);
    } else {
      data = await request(""); 
    }
  }
  return asList(data).map(sanitizeProduct).filter(Boolean);
}

// ===== scripts/navbar.js =====
function initNavbar() {
  const links = document.querySelectorAll(".nav-links a");
  const navLinks = document.getElementById("navLinks");
  const menuBtn = document.getElementById("menuBtn");

  links.forEach(link => {
    link.addEventListener("click", () => {
      links.forEach(l => l.classList.remove("active"));
      link.classList.add("active");
      navLinks.classList.remove("open");
      menuBtn.setAttribute("aria-expanded", "false");
    });
  });

  menuBtn.addEventListener("click", () => {
    const open = navLinks.classList.toggle("open");
    menuBtn.setAttribute("aria-expanded", String(open));
  });

  const setNavHeight = () =>
    document.documentElement.style.setProperty("--nav-h", document.querySelector(".navbar").offsetHeight + "px");
  setNavHeight();
  window.addEventListener("resize", setNavHeight);
}

// ===== scripts/categories.js =====
const CATEGORY_TINTS = ["#fdf1e7", "#e9f0e4", "#fbe9e9", "#eceefc", "#fbf5d9"];

function categoryCard(c, i) {
  const catKey = (c.name || "").toLowerCase().trim().replace(/\s+/g, '-');

  const card = el("a", {
    className: "cat-card",
    attrs: { href: `#cat-${catKey}` },
    children: [
      el("div", { className: "cat-img", attrs: { "aria-hidden": "true" }, children: [picture(c.image)] }),
      el("h3", { text: c.name }),
      el("p", { text: `${c.itemCount} Items` }),
      el("span", { className: "cat-go", attrs: { "aria-hidden": "true" }, children: [icon("arrow")] })
    ]
  });
  card.style.setProperty("--tint", CATEGORY_TINTS[i % CATEGORY_TINTS.length]);
  return card;
}

async function loadCategories() {
  const grid = document.getElementById("catGrid");
  try {
    const categories = await getCategories();
    grid.replaceChildren(...categories.map(categoryCard));
    document.getElementById("footCats").replaceChildren(...categories.map(c =>
      el("li", { children: [el("a", { text: c.name, attrs: { href: `#cat-${c.name.toLowerCase().trim().replace(/\s+/g, '-')}` } })] })));
  } catch (err) {
    console.error(err);
    grid.replaceChildren(el("p", { className: "load-error", text: "Could not load categories. Please try again later." }));
  }
}

// ===== scripts/products.js =====
const productCache = new Map();   
let latestRequest = 0;            

function productCard(p) {
  const children = [];
  if (p.discount > 0) children.push(el("span", { className: "discount", text: `-${p.discount}%` }));

  children.push(
    el("button", {
      className: "fav",
      attrs: { type: "button", "aria-label": "Add to favourites", "aria-pressed": "false" },
      children: [icon("heart")]
    }),
    el("div", { className: "p-img", attrs: { "aria-hidden": "true" }, children: [picture(p.image)] }),
    el("div", {
      className: "p-body",
      children: [
        el("p", { className: "p-cat", text: p.category }),
        el("h3", { className: "p-name", text: p.name }),
        el("p", { className: "p-price", children: [document.createTextNode(money(p.price)), el("small", { text: "/kg" })] })
      ]
    }),
    el("button", {
      className: "add-cart",
      attrs: { type: "button", "data-id": p.id, "aria-label": `Add ${p.name} to cart` },
      children: [icon("cart")]
    })
  );
  return el("article", { className: "product-card", children });
}

async function loadProducts(params = {}) {
  const message = document.getElementById("noResults");
  const myRequest = ++latestRequest;

  const categoryKeys = ["vegetables", "fruits", "dairy", "meat", "seafood", "bakery", "snacks", "general"];

  try {
    const list = await getProducts(params);
    if (myRequest !== latestRequest) return;        
    
    list.forEach(p => productCache.set(p.id, p));

    const groups = {};
    categoryKeys.forEach(k => groups[k] = []);

    list.forEach(p => {
      let rawCat = (p.category || "general").toLowerCase().trim();
      let matchedKey = categoryKeys.find(k => rawCat.includes(k)) || "general";
      groups[matchedKey].push(p);
    });

    categoryKeys.forEach(key => {
      const grid = document.getElementById(`productGrid-${key}`);
      const section = grid ? grid.closest(".products-section") : null;
      const items = groups[key];

      if (grid) {
        grid.replaceChildren(...items.map(productCard));
      }
      if (section) {
        section.hidden = items.length === 0;
      }
    });

    message.textContent = "No items match your search.";
    message.hidden = list.length > 0;
  } catch (err) {
    if (myRequest !== latestRequest) return;
    console.error(err);
    categoryKeys.forEach(key => {
      const grid = document.getElementById(`productGrid-${key}`);
      if (grid) grid.replaceChildren();
    });
    message.textContent = "Could not load products. Please try again later.";
    message.hidden = false;
  }
}

function initProducts() {
  document.getElementById("products").addEventListener("click", e => {
    const fav = e.target.closest(".fav");
    if (fav) fav.setAttribute("aria-pressed", String(fav.getAttribute("aria-pressed") !== "true"));
  });

  const input = document.getElementById("searchInput");
  const goToProducts = () => document.getElementById("products").scrollIntoView({ behavior: "smooth" });
  let lastQuery = "", timer;

  input.addEventListener("input", () => {
    clearTimeout(timer);
    timer = setTimeout(() => {
      const q = cleanText(input.value, 100);
      loadProducts({ search: q });
      if (q && !lastQuery) goToProducts();           
      lastQuery = q;
    }, 250);
  });
  document.getElementById("searchForm").addEventListener("submit", e => {
    e.preventDefault();
    goToProducts();
  });
}

// ===== scripts/cart.js =====
const MAX_QTY = 99;
const cart = new Map();   

function cartTotals() {
  let total = 0, count = 0;
  cart.forEach(({ product, qty }) => { total += product.price * qty; count += qty; });
  return { total, count };
}

function cartItemRow({ product: p, qty }) {
  const stepper = (act, label, symbol) => el("button", {
    text: symbol,
    attrs: { type: "button", "data-act": act, "data-id": p.id, "aria-label": label }
  });

  return el("div", {
    className: "cart-item",
    children: [
      el("div", { className: "ci-img", attrs: { "aria-hidden": "true" }, children: [picture(p.image)] }),
      el("div", {
        className: "ci-info",
        children: [
          el("p", { className: "ci-name", text: p.name }),
          el("p", { className: "ci-price", text: money(p.price) })
        ]
      }),
      el("div", {
        className: "qty",
        children: [
          stepper("dec", "Decrease quantity", "\u2212"),
          el("span", { text: String(qty), attrs: { "aria-live": "polite" } }),
          stepper("inc", "Increase quantity", "+")
        ]
      })
    ]
  });
}

function renderCart() {
  const { total, count } = cartTotals();
  const list = document.getElementById("cartItems");

  if (cart.size) {
    list.replaceChildren(...[...cart.values()].map(cartItemRow));
  } else {
    list.replaceChildren(el("p", {
      className: "cart-empty",
      children: [
        document.createTextNode("Your cart is empty."),
        document.createElement("br"),
        document.createTextNode("Add something from Products.")
      ]
    }));
  }

  document.getElementById("cartSubtotal").textContent = money(total);
  const badge = document.getElementById("cartCount");
  badge.textContent = String(count);
  badge.hidden = count === 0;
}

function setCartOpen(open) {
  document.getElementById("cartPanel").classList.toggle("open", open);
  document.getElementById("cartPanel").setAttribute("aria-hidden", String(!open));
  document.getElementById("cartBtn").setAttribute("aria-expanded", String(open));
  document.body.classList.toggle("cart-open", open);
}

function addToCart(id) {
  const product = productCache.get(id);       
  if (!product) return;
  const wasEmpty = cart.size === 0;
  const qty = Math.min(MAX_QTY, (cart.get(id)?.qty || 0) + 1);
  cart.set(id, { product, qty });
  renderCart();
  if (wasEmpty) setCartOpen(true);            
}

function changeQuantity(id, delta) {
  const item = cart.get(id);
  if (!item) return;
  item.qty = Math.min(MAX_QTY, item.qty + delta);
  if (item.qty <= 0) cart.delete(id);
  renderCart();
}

function clearCart() {
  cart.clear();
  localStorage.removeItem("aislewise_cart_ids");
  renderCart();
}

function initCart() {
  document.getElementById("cartBtn").addEventListener("click", () =>
    setCartOpen(!document.getElementById("cartPanel").classList.contains("open")));

  document.getElementById("clearCartBtn").addEventListener("click", clearCart);

  document.getElementById("products").addEventListener("click", e => {
    const add = e.target.closest(".add-cart");
    if (add) addToCart(add.getAttribute("data-id"));
  });

  document.getElementById("cartItems").addEventListener("click", e => {
    const b = e.target.closest("button[data-act]");
    if (!b) return;
    const act = b.getAttribute("data-act");
    if (act === "inc" || act === "dec") changeQuantity(b.getAttribute("data-id"), act === "inc" ? 1 : -1);
  });

  renderCart();
}

async function fetchStoreRoute(productIds) {
  const res = await fetch("http://localhost:8080/api/products/route", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json"
    },
    body: JSON.stringify(productIds)
  });
  if (!res.ok) throw new Error("Failed to calculate route");
  return await res.json(); 
}

document.getElementById("mapBtn").addEventListener("click", () => {
  const productIds = [];
  cart.forEach(({ product, qty }) => {
    for (let i = 0; i < qty; i++) {
      productIds.push(Number(product.id)); 
    }
  });

  if (productIds.length === 0) {
    alert("Your cart is empty!");
    return;
  }

  localStorage.setItem("aislewise_cart_ids", JSON.stringify(productIds));
  window.open("map.html", "_blank");
});

// ===== scripts/main.js =====
initNavbar();
initCart();
initProducts();
loadCategories();
loadProducts();