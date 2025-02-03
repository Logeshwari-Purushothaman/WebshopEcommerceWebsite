<script>
  import { onMount } from 'svelte';

  let products = [];
  let error = null;
  const catalogUrl = 'http://localhost:8080/saas/catalog';

  async function fetchProducts() {
    try {
      const response = await fetch(catalogUrl, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'X-API-KEY': 'howDoItellYou' // Replace with your actual API key
        }
      });
      if (!response.ok) {
        throw new Error(`Error fetching products: ${response.statusText}`);
      }
      products = await response.json();
    } catch (err) {
      error = err.message;
    }
  }

  onMount(() => {
    fetchProducts();
  });
</script>

<main>
  <h1>Product Catalog</h1>
  {#if error}
    <p class="error">{error}</p>
  {:else if products.length === 0}
    <p>Loading products...</p>
  {:else}
    <div class="product-grid">
      {#each products as product}
        <div class="product-card">
          <h2>{product.name}</h2>
          <p class="price">{product.price} {product.currency}</p>
          <p class="description">{product.description}</p>
          <p class="category">Category: {product.category}</p>
          <p class="stock">In stock: {product.stock}</p>
        </div>
      {/each}
    </div>
  {/if}
</main>

<style>
  main {
    padding: 20px;
    font-family: Arial, sans-serif;
  }
  h1 {
    color: #333;
    text-align: center;
  }
  .error {
    color: red;
    font-weight: bold;
    text-align: center;
  }
  .product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
    margin-top: 20px;
  }
  .product-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 15px;
    background-color: #f9f9f9;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
  }
  .product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0,0,0,0.2);
  }
  .product-card h2 {
    margin-top: 0;
    color: #444;
    font-size: 1.2em;
  }
  .price {
    font-weight: bold;
    color: #4CAF50;
    margin-bottom: 10px;
  }
  .description {
    font-style: italic;
    margin-bottom: 10px;
    color: #555;
  }
  .category, .stock {
    font-size: 0.9em;
    color: #666;
    margin-bottom: 5px;
  }
</style>
