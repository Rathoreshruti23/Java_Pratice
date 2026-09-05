// ✅ API base (common part)
const API_BASE_URL = "http://localhost:8080/api/product";

async function fetchProducts() {
  try {
    const response = await fetch(API_BASE_URL);
    if (!response.ok) throw new Error("Failed to fetch products");

    const products = await response.json();
    const productList = document.getElementById("productList");
    productList.innerHTML = "";

    products.forEach(p => {
      const div = document.createElement("div");
      div.classList.add("product-card");
      div.innerHTML = `
        <strong>${p.productName}</strong> — ₹${p.price}
        <br>MFD: ${p.mfd} | EXP: ${p.exp}
        <button onclick="deleteProduct(${p.id})"
          style="float:right;background:red;color:white;border:none;border-radius:4px;padding:5px;">
          Delete
        </button>
      `;
      productList.appendChild(div);
    });
  } catch (error) {
    console.error("❌ Error fetching products:", error);
  }
}

document.getElementById("productForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const product = {
    productName: document.getElementById("name").value,
    price: parseInt(document.getElementById("price").value),
    mfd: document.getElementById("mfd").value,
    exp: document.getElementById("exp").value
  };

  try {
    // ✅ Notice: we are posting to /api/product/create
    const response = await fetch(`${API_BASE_URL}/create`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(product)
    });

    if (!response.ok) throw new Error("Failed to add product");

    console.log("✅ Product added successfully");
    document.getElementById("productForm").reset();
    fetchProducts();

  } catch (error) {
    console.error("❌ Error adding product:", error);
    alert("Error adding product! Check console for details.");
  }
});

async function deleteProduct(id) {
  try {
    const response = await fetch(`${API_BASE_URL}/${id}`, { method: "DELETE" });
    if (!response.ok) throw new Error("Failed to delete product");

    console.log("🗑️ Product deleted");
    fetchProducts();
  } catch (error) {
    console.error("❌ Error deleting product:", error);
  }
}

fetchProducts();
