let lastId = null;
const limit = 10;
const container = document.getElementById('items-container');
const loading = document.getElementById('loading');

async function fetchItems() {
    const url = new URL('/items', window.location.origin);
    if (lastId !== null) {
        url.searchParams.append('lastId', lastId);
    }
    url.searchParams.append('limit', limit);

    const response = await fetch(url);
    const data = await response.json();
    return data;
}

async function loadItems() {
    loading.style.display = 'block';
    const data = await fetchItems();
    data.items.forEach(item => {
        const div = document.createElement('div');
        div.className = 'item';
        div.textContent = `ID: ${item.id}, Name: ${item.name}`;
        container.appendChild(div);
    });
    lastId = data.lastId;
    loading.style.display = 'none';
}

window.addEventListener('scroll', () => {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
        loadItems();
    }
});

// Initial load
loadItems();