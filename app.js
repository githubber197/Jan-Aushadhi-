
// ─── MEDICINE DATABASE ───────────────────────────────────────────────────────
const medicines = [
  { id:1, brand:"Crocin",       generic:"Paracetamol",        salt:"Paracetamol 500mg",   mfr:"GSK",       brandPrice:45,  genericPrice:5,   cat:"pain",     icon:"🤒" },
  { id:2, brand:"Dolo 650",     generic:"Paracetamol",        salt:"Paracetamol 650mg",   mfr:"Micro Labs", brandPrice:30,  genericPrice:4,   cat:"pain",     icon:"🤒" },
  { id:3, brand:"Combiflam",    generic:"Ibuprofen+Paracetamol",salt:"Ibuprofen 400mg",   mfr:"Sanofi",    brandPrice:58,  genericPrice:8,   cat:"pain",     icon:"💊" },
  { id:4, brand:"Metformin SR", generic:"Metformin",          salt:"Metformin 500mg SR",  mfr:"USV",       brandPrice:120, genericPrice:15,  cat:"diabetes", icon:"🩸" },
  { id:5, brand:"Glycomet",     generic:"Metformin",          salt:"Metformin 500mg",     mfr:"USV",       brandPrice:95,  genericPrice:12,  cat:"diabetes", icon:"🩸" },
  { id:6, brand:"Januvia",      generic:"Sitagliptin",        salt:"Sitagliptin 100mg",   mfr:"MSD",       brandPrice:420, genericPrice:55,  cat:"diabetes", icon:"🩸" },
  { id:7, brand:"Telma",        generic:"Telmisartan",        salt:"Telmisartan 40mg",    mfr:"Glenmark",  brandPrice:110, genericPrice:12,  cat:"bp",       icon:"❤️" },
  { id:8, brand:"Amlodipine",   generic:"Amlodipine",         salt:"Amlodipine 5mg",      mfr:"Cipla",     brandPrice:85,  genericPrice:8,   cat:"bp",       icon:"❤️" },
  { id:9, brand:"Stamlo",       generic:"Amlodipine",         salt:"Amlodipine 5mg",      mfr:"Dr Reddy",  brandPrice:78,  genericPrice:8,   cat:"bp",       icon:"❤️" },
  { id:10,brand:"Augmentin",    generic:"Amoxicillin+Clav",   salt:"Amox 500+Clav 125mg", mfr:"GSK",       brandPrice:280, genericPrice:45,  cat:"antibiotic",icon:"🦠" },
  { id:11,brand:"Azithral",     generic:"Azithromycin",       salt:"Azithromycin 500mg",  mfr:"Alembic",   brandPrice:135, genericPrice:20,  cat:"antibiotic",icon:"🦠" },
  { id:12,brand:"Zithromax",    generic:"Azithromycin",       salt:"Azithromycin 250mg",  mfr:"Pfizer",    brandPrice:160, genericPrice:18,  cat:"antibiotic",icon:"🦠" },
  { id:13,brand:"Becosules",    generic:"Vit B Complex",      salt:"Vit B Complex+C",     mfr:"Pfizer",    brandPrice:145, genericPrice:22,  cat:"vitamin",  icon:"💛" },
  { id:14,brand:"Limcee",       generic:"Vitamin C",          salt:"Ascorbic Acid 500mg", mfr:"Abbott",    brandPrice:62,  genericPrice:8,   cat:"vitamin",  icon:"🍋" },
  { id:15,brand:"Pantop",       generic:"Pantoprazole",       salt:"Pantoprazole 40mg",   mfr:"Aristo",    brandPrice:95,  genericPrice:10,  cat:"pain",     icon:"💊" },
  { id:16,brand:"Omez",         generic:"Omeprazole",         salt:"Omeprazole 20mg",     mfr:"Dr Reddy",  brandPrice:72,  genericPrice:7,   cat:"pain",     icon:"💊" },
  { id:17,brand:"Atorva",       generic:"Atorvastatin",       salt:"Atorvastatin 10mg",   mfr:"Zydus",     brandPrice:98,  genericPrice:10,  cat:"bp",       icon:"❤️" },
  { id:18,brand:"Lipitor",      generic:"Atorvastatin",       salt:"Atorvastatin 20mg",   mfr:"Pfizer",    brandPrice:310, genericPrice:18,  cat:"bp",       icon:"❤️" },
  { id:19,brand:"Allegra",      generic:"Fexofenadine",       salt:"Fexofenadine 120mg",  mfr:"Sanofi",    brandPrice:115, genericPrice:14,  cat:"pain",     icon:"🤧" },
  { id:20,brand:"Cetriz",       generic:"Cetirizine",         salt:"Cetirizine 10mg",     mfr:"Cipla",     brandPrice:38,  genericPrice:3,   cat:"pain",     icon:"🤧" },
];

// ─── JAN-AUSHADHI STORES ─────────────────────────────────────────────────────
const stores = [
  { id:1, name:"PMBJP Kendra — Andheri West",   addr:"Shop 4, Andheri Market, Mumbai", dist:"0.8 km", open:true,  pin:{top:"38%",left:"44%"} },
  { id:2, name:"Jan-Aushadhi Store — Bandra",   addr:"Plot 12, Hill Rd, Bandra, Mumbai",dist:"2.1 km",open:true,  pin:{top:"55%",left:"62%"} },
  { id:3, name:"PMBJP Kendra — Goregaon East",  addr:"Near Station, Goregaon E, Mumbai",dist:"4.5 km",open:false, pin:{top:"22%",left:"30%"} },
  { id:4, name:"Jan-Aushadhi — Malad West",     addr:"Orlem Market, Malad W, Mumbai",   dist:"6.2 km",open:true,  pin:{top:"18%",left:"55%"} },
];

// ─── HEALTH LITERACY CARDS ───────────────────────────────────────────────────
const infoCards = [
  { emoji:"🔬", title:"Same Active Ingredient",        short:"Generic medicines contain the exact same active ingredient, dosage, and strength as branded drugs.", body:"The CDSCO (Central Drugs Standard Control Organisation) mandates that every generic drug prove bioequivalence to its branded counterpart. This means the drug reaches the bloodstream at the same rate and in the same concentration — making it therapeutically identical." },
  { emoji:"💰", title:"Why Are Generics Cheaper?",     short:"Price difference is due to R&D costs, not quality. Generics skip expensive clinical trials.", body:"Branded drug companies spend billions on research and clinical trials. Once their patent expires (typically 20 years), generic manufacturers can produce the same molecule without those R&D costs — passing the savings directly to patients. The medicine is the same; only the price differs." },
  { emoji:"🏛️", title:"Government Quality Guarantee", short:"Jan-Aushadhi medicines are tested in NABL-accredited labs before reaching shelves.", body:"Every medicine in the PMBJP basket undergoes rigorous quality testing at NABL-accredited laboratories. The Bureau of Pharma PSUs of India (BPPI) monitors quality at every stage — from procurement to distribution — ensuring you get a medicine that meets the same standards as any branded drug." },
  { emoji:"🌍", title:"WHO Endorses Generics",         short:"The World Health Organization actively promotes generic medicines for universal healthcare.", body:"The WHO Essential Medicines List is entirely composed of generic medicines. Countries worldwide — including the UK's NHS — rely on generics as the backbone of their healthcare systems. Generics save global health systems over $300 billion annually while maintaining treatment quality." },
  { emoji:"📋", title:"How to Read a Prescription",   short:"Doctors write brand names, but the salt name (active ingredient) is what matters.", body:"When a doctor writes 'Crocin', the active ingredient is Paracetamol. Ask your pharmacist for the generic salt name. Jan-Aushadhi Finder maps 500+ branded names to their generic salts instantly, helping you make informed choices without needing a medical degree." },
  { emoji:"🔔", title:"Never Miss a Refill",           short:"Use the My Medicines tab to set monthly reminders so you never run out of medication.", body:"Medication non-adherence is a major public health crisis. Studies show that 50% of patients with chronic conditions stop taking medicines regularly due to cost or forgetfulness. Our refill tracker addresses both — by cutting costs by up to 90% and sending you a reminder 2 days before you run out." },
];

// ─── STATE ───────────────────────────────────────────────────────────────────
let currentScreen = "screen-home";
let myMeds = JSON.parse(localStorage.getItem("myMeds") || "[]");
let activeCategory = "all";
let obStep = 0;
let currentMedicine = null;

// ─── LEVENSHTEIN FUZZY SEARCH ────────────────────────────────────────────────
function levenshtein(a, b) {
  const m = a.length, n = b.length;
  const dp = Array.from({length:m+1}, (_,i) => Array.from({length:n+1},(_,j)=>i===0?j:j===0?i:0));
  for (let i=1;i<=m;i++) for (let j=1;j<=n;j++)
    dp[i][j] = a[i-1]===b[j-1] ? dp[i-1][j-1] : 1+Math.min(dp[i-1][j],dp[i][j-1],dp[i-1][j-1]);
  return dp[m][n];
}

function fuzzySearch(query) {
  if (!query || query.length < 2) return [];
  const q = query.toLowerCase();
  return medicines.filter(m => {
    const brand = m.brand.toLowerCase();
    const generic = m.generic.toLowerCase();
    const salt = m.salt.toLowerCase();
    if (brand.includes(q) || generic.includes(q) || salt.includes(q)) return true;
    if (q.length >= 4) {
      const dist = Math.min(levenshtein(q, brand.slice(0,q.length)), levenshtein(q, generic.slice(0,q.length)));
      return dist <= 2;
    }
    return false;
  }).slice(0,6);
}

// ─── SAVINGS HELPERS ─────────────────────────────────────────────────────────
function savings(m)  { return m.brandPrice - m.genericPrice; }
function savingsPct(m){ return Math.round((savings(m)/m.brandPrice)*100); }
function daysUntil(dateStr) {
  const diff = new Date(dateStr) - new Date();
  return Math.ceil(diff/(1000*60*60*24));
}

// ─── RENDER POPULAR MEDICINES ────────────────────────────────────────────────
function renderPopular() {
  const list = document.getElementById("popular-list");
  const items = activeCategory === "all" ? medicines.slice(0,8) : medicines.filter(m=>m.cat===activeCategory);
  list.innerHTML = items.map(m => `
    <div class="med-card" onclick="showDetail(${m.id})">
      <div class="med-icon">${m.icon}</div>
      <div class="med-info">
        <div class="med-name">${m.brand}</div>
        <div class="med-generic">${m.salt}</div>
        <div class="med-prices">
          <span class="price-brand">₹${m.brandPrice}</span>
          <span class="price-generic">₹${m.genericPrice}</span>
          <span class="save-badge">Save ${savingsPct(m)}%</span>
        </div>
      </div>
    </div>`).join("");
}

// ─── SEARCH HANDLER ──────────────────────────────────────────────────────────
document.getElementById("medicine-search").addEventListener("input", function() {
  const q = this.value.trim();
  const box = document.getElementById("search-results");
  if (!q) { box.classList.add("hidden"); return; }
  const results = fuzzySearch(q);
  if (!results.length) {
    box.innerHTML = `<div class="search-result-item" onclick="showGenAI('${q}')">
      <div class="sri-name">🤖 Ask GenAI for "<em>${q}</em>"</div>
      <div class="sri-generic">Not in local DB — use AI fallback</div>
    </div>`;
  } else {
    box.innerHTML = results.map(m => `
      <div class="search-result-item" onclick="showDetail(${m.id})">
        <span class="sri-save">Save ${savingsPct(m)}%</span>
        <div class="sri-name">${m.brand}</div>
        <div class="sri-generic">${m.salt} · ₹${m.genericPrice}</div>
      </div>`).join("");
  }
  box.classList.remove("hidden");
});

document.addEventListener("click", e => {
  if (!e.target.closest(".search-card")) document.getElementById("search-results").classList.add("hidden");
});

// ─── MEDICINE DETAIL ─────────────────────────────────────────────────────────
function showDetail(id) {
  document.getElementById("search-results").classList.add("hidden");
  document.getElementById("medicine-search").value = "";
  currentMedicine = medicines.find(m => m.id === id);
  if (!currentMedicine) return;
  const m = currentMedicine;
  const sv = savings(m), sp = savingsPct(m);
  document.getElementById("detail-content").innerHTML = `
    <div class="detail-card">
      <div style="display:flex;align-items:center;gap:12px;margin-bottom:12px">
        <div style="font-size:48px">${m.icon}</div>
        <div>
          <div class="detail-brand-name">${m.brand}</div>
          <span class="detail-generic-badge">Generic: ${m.generic}</span>
          <div class="detail-manufacturer">by ${m.mfr} · ${m.salt}</div>
        </div>
      </div>
      <div class="price-comparison">
        <div class="price-box branded">
          <div class="price-box-label">Branded MRP</div>
          <div class="price-box-amount">₹${m.brandPrice}</div>
          <div class="price-box-per">per strip</div>
        </div>
        <div class="price-box generic">
          <div class="price-box-label">Jan-Aushadhi</div>
          <div class="price-box-amount">₹${m.genericPrice}</div>
          <div class="price-box-per">per strip</div>
        </div>
      </div>
      <div class="savings-strip">
        <div class="savings-strip-label">YOU SAVE</div>
        <div class="savings-strip-amount">₹${sv} <span style="font-size:16px;font-weight:500">(${sp}%)</span></div>
        <div class="savings-strip-pct">per strip on this medicine</div>
      </div>
    </div>
    <div class="calc-card">
      <h3>💰 Monthly Savings Calculator</h3>
      <div class="calc-row">
        <label>Strips per month</label>
        <input type="number" class="calc-input" id="calc-qty" value="2" min="1" max="30" oninput="calcSavings()" />
      </div>
      <div class="calc-results">
        <div class="calc-result-box">
          <div class="calc-result-label">Monthly Saving</div>
          <div class="calc-result-val" id="monthly-save">₹${sv*2}</div>
        </div>
        <div class="calc-result-box">
          <div class="calc-result-label">Annual Saving</div>
          <div class="calc-result-val" id="annual-save">₹${sv*2*12}</div>
        </div>
      </div>
    </div>
    <div class="detail-actions">
      <button class="btn-primary" onclick="navigateTo('screen-map')">🗺️ Find Nearest Kendra</button>
      <button class="btn-ghost" onclick="addToMyMeds('${m.brand}')">+ Add to My Medicines</button>
    </div>`;
  navigateTo("screen-detail");
}

function calcSavings() {
  const m = currentMedicine; if (!m) return;
  const qty = parseInt(document.getElementById("calc-qty").value) || 1;
  const sv = savings(m);
  document.getElementById("monthly-save").textContent = "₹"+(sv*qty);
  document.getElementById("annual-save").textContent  = "₹"+(sv*qty*12);
}

function addToMyMeds(name) {
  showToast("✅ Added to My Medicines!");
}

// ─── GENAI FALLBACK ──────────────────────────────────────────────────────────
function showGenAI(query) {
  document.getElementById("search-results").classList.add("hidden");
  document.getElementById("medicine-search").value = "";
  document.getElementById("detail-content").innerHTML = `
    <div class="genai-card">
      <div class="genai-header"><span class="genai-icon">🤖</span><span class="genai-label">GenAI Suggestion (Gemini)</span></div>
      <div class="genai-loader"><div class="splash-loader"><span></span><span></span><span></span></div><span>Searching generic equivalent…</span></div>
    </div>`;
  navigateTo("screen-detail");
  setTimeout(() => {
    const result = getGenAIResult(query);
    document.querySelector(".genai-card").innerHTML = `
      <div class="genai-header"><span class="genai-icon">🤖</span><span class="genai-label">GenAI Suggestion (Gemini)</span></div>
      <div class="genai-result">Likely generic salt: <strong>${result.salt}</strong></div>
      <div style="margin-top:8px;font-size:13px;color:#555">Estimated Jan-Aushadhi Price: <strong>₹${result.price}</strong> vs Branded ₹${result.brandPrice}</div>
      <div class="genai-disclaimer">⚠️ AI-generated suggestion. Please verify with your pharmacist or doctor before purchase.</div>`;
  }, 2200);
}

function getGenAIResult(q) {
  const map = {"dolo":  {salt:"Paracetamol 650mg",price:4,brandPrice:30},
               "voveran":{salt:"Diclofenac 50mg",price:6,brandPrice:55},
               "default":{salt:"Please verify with pharmacist",price:"N/A",brandPrice:"N/A"}};
  const key = Object.keys(map).find(k => q.toLowerCase().includes(k)) || "default";
  return map[key];
}

// ─── STORE LOCATOR ───────────────────────────────────────────────────────────
function renderStores() {
  const pins = document.getElementById("map-pins");
  const list = document.getElementById("stores-list");
  pins.innerHTML = stores.map(s => `
    <div class="store-pin" style="top:${s.pin.top};left:${s.pin.left}" title="${s.name}" onclick="scrollToStore(${s.id})">
      ${s.open ? "🟢" : "🔴"}
    </div>`).join("");
  list.innerHTML = stores.map(s => `
    <div class="store-card" id="store-${s.id}">
      <div class="store-header">
        <div class="store-name">${s.name}</div>
        <span class="store-status ${s.open ? "open":"closed"}">${s.open ? "Open":"Closed"}</span>
      </div>
      <div class="store-addr">📍 ${s.addr}</div>
      <div class="store-dist">🚶 ${s.dist} away</div>
      <div class="store-actions">
        <button class="btn-sm" onclick="checkStock(${s.id})">📦 Check Stock</button>
        <button class="btn-sm outline" onclick="getDirections('${s.addr}')">🗺️ Directions</button>
      </div>
      <div class="stock-response" id="stock-resp-${s.id}"></div>
    </div>`).join("");
}

function scrollToStore(id) {
  document.getElementById("store-"+id)?.scrollIntoView({behavior:"smooth",block:"center"});
}

function checkStock(id) {
  const el = document.getElementById("stock-resp-"+id);
  el.textContent = "Checking availability…";
  el.style.display = "block";
  el.style.background = "#fff8e1";
  el.style.borderColor = "#ffe082";
  el.style.color = "#f57f17";
  setTimeout(() => {
    const meds = currentMedicine ? currentMedicine.brand : "Paracetamol";
    el.textContent = `✅ ${meds} (generic) is available at this Kendra. Stock: 50+ units.`;
    el.style.background = "#e8f5e9";
    el.style.borderColor = "#c8e6c9";
    el.style.color = "#2e7d32";
  }, 3000);
}

function getDirections(addr) {
  window.open("https://maps.google.com/?q="+encodeURIComponent(addr), "_blank");
}

document.getElementById("gps-btn").addEventListener("click", () => {
  showToast("📍 Using your current location…");
});

// ─── MY MEDICINES (REFILL) ───────────────────────────────────────────────────
function renderMyMeds() {
  const list = document.getElementById("refill-list");
  const empty = document.getElementById("refill-empty");
  if (!myMeds.length) { list.innerHTML = ""; empty.classList.remove("hidden"); return; }
  empty.classList.add("hidden");
  list.innerHTML = myMeds.map((m,i) => {
    const days = daysUntil(m.date);
    const cls = days < 0 ? "overdue" : days <= 2 ? "due" : "date";
    const label = days < 0 ? `Overdue ${Math.abs(days)}d` : days === 0 ? "Due Today!" : days <= 2 ? `Due in ${days}d` : new Date(m.date).toLocaleDateString("en-IN",{day:"numeric",month:"short"});
    return `<div class="refill-card">
      <div class="refill-card-header">
        <div class="refill-med-name">💊 ${m.name}</div>
        <button class="refill-del" onclick="deleteMed(${i})">🗑️</button>
      </div>
      <div class="refill-meta">
        <span class="refill-tag ${cls}">📅 ${label}</span>
        <span class="refill-tag qty">📦 ${m.qty} tablets/mo</span>
      </div>
    </div>`;
  }).join("");
}

document.getElementById("add-med-btn").addEventListener("click", () => {
  document.getElementById("add-med-modal").classList.remove("hidden");
  const today = new Date(); today.setDate(today.getDate()+30);
  document.getElementById("new-med-date").value = today.toISOString().split("T")[0];
});

document.getElementById("cancel-med").addEventListener("click", () => {
  document.getElementById("add-med-modal").classList.add("hidden");
});

document.getElementById("save-med").addEventListener("click", () => {
  const name = document.getElementById("new-med-name").value.trim();
  const date = document.getElementById("new-med-date").value;
  const qty  = document.getElementById("new-med-qty").value || "30";
  if (!name || !date) { showToast("⚠️ Please fill all fields"); return; }
  myMeds.push({name,date,qty});
  localStorage.setItem("myMeds", JSON.stringify(myMeds));
  document.getElementById("add-med-modal").classList.add("hidden");
  document.getElementById("new-med-name").value = "";
  renderMyMeds();
  showToast("✅ Reminder set for "+name);
});

function deleteMed(i) {
  myMeds.splice(i,1);
  localStorage.setItem("myMeds", JSON.stringify(myMeds));
  renderMyMeds();
}

// ─── HEALTH LITERACY ─────────────────────────────────────────────────────────
function renderInfoCards() {
  document.getElementById("info-cards").innerHTML = infoCards.map((c,i)=> `
    <div class="info-card" onclick="toggleCard(${i})" id="ic-${i}">
      <span class="info-card-emoji">${c.emoji}</span>
      <h3>${c.title}</h3>
      <p>${c.short}</p>
      <div class="info-card-body">${c.body}</div>
    </div>`).join("");
}

function toggleCard(i) {
  const card = document.getElementById("ic-"+i);
  card.classList.toggle("expanded");
  card.querySelector(".info-card-body").style.display = card.classList.contains("expanded") ? "block" : "none";
}

// ─── NAVIGATION ──────────────────────────────────────────────────────────────
function navigateTo(screenId) {
  document.querySelectorAll(".screen-panel").forEach(s => s.classList.remove("active"));
  document.getElementById(screenId).classList.add("active");
  document.querySelectorAll(".nav-item").forEach(n => {
    n.classList.toggle("active", n.dataset.screen === screenId);
  });
  currentScreen = screenId;
  if (screenId === "screen-meds") renderMyMeds();
}

document.querySelectorAll(".nav-item").forEach(btn => {
  btn.addEventListener("click", () => navigateTo(btn.dataset.screen));
});

document.getElementById("back-to-home").addEventListener("click", () => navigateTo("screen-home"));

// ─── CHIPS ───────────────────────────────────────────────────────────────────
document.querySelectorAll(".chip").forEach(chip => {
  chip.addEventListener("click", () => {
    document.querySelectorAll(".chip").forEach(c => c.classList.remove("active"));
    chip.classList.add("active");
    activeCategory = chip.dataset.cat;
    renderPopular();
  });
});

// ─── SCAN BUTTON ─────────────────────────────────────────────────────────────
document.getElementById("scan-btn").addEventListener("click", () => {
  const overlay = document.createElement("div");
  overlay.className = "scan-overlay";
  overlay.innerHTML = `<div class="scan-frame"><div class="scan-line"></div></div>
    <h3>Point at Prescription</h3><p>Align medicine name within frame</p>
    <button class="btn-primary" onclick="simulateScan(this)">📷 Simulate Scan</button>`;
  document.body.appendChild(overlay);
  window._scanOverlay = overlay;
});

window.simulateScan = function(btn) {
  btn.textContent = "Scanning…";
  setTimeout(() => {
    window._scanOverlay?.remove();
    document.getElementById("medicine-search").value = "Crocin";
    document.getElementById("medicine-search").dispatchEvent(new Event("input"));
    showToast("📷 Prescription scanned: Crocin detected");
  }, 1500);
};

// ─── TOAST ───────────────────────────────────────────────────────────────────
function showToast(msg) {
  const t = document.getElementById("stock-toast");
  document.getElementById("toast-msg").textContent = msg;
  t.classList.remove("hidden");
  clearTimeout(window._toastTimer);
  window._toastTimer = setTimeout(() => t.classList.add("hidden"), 3000);
}

// ─── NOTIFICATIONS BELL ──────────────────────────────────────────────────────
document.getElementById("notif-btn").addEventListener("click", () => {
  const due = myMeds.filter(m => daysUntil(m.date) <= 2);
  if (due.length) showToast(`🔔 ${due.length} refill(s) due soon!`);
  else showToast("🔔 No upcoming refills");
});

// ─── ONBOARDING ──────────────────────────────────────────────────────────────
function startApp() {
  document.getElementById("onboarding").classList.add("hidden");
  document.getElementById("app").classList.remove("hidden");
  renderPopular();
  renderStores();
  renderInfoCards();
}

document.getElementById("ob-skip").addEventListener("click", startApp);

document.getElementById("ob-next").addEventListener("click", () => {
  document.getElementById("ob"+obStep).classList.remove("active");
  document.getElementById("dot"+obStep).classList.remove("active");
  obStep++;
  if (obStep >= 3) { startApp(); return; }
  document.getElementById("ob"+obStep).classList.add("active");
  document.getElementById("dot"+obStep).classList.add("active");
  if (obStep === 2) document.getElementById("ob-next").textContent = "Get Started";
});

// ─── INIT ─────────────────────────────────────────────────────────────────────
setTimeout(() => {
  document.getElementById("splash-screen").classList.add("hidden");
  const seen = localStorage.getItem("onboarded");
  if (seen) { startApp(); }
  else {
    document.getElementById("onboarding").classList.remove("hidden");
    localStorage.setItem("onboarded","1");
  }
}, 2500);
